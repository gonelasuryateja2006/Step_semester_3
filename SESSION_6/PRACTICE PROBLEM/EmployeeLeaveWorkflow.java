import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EmployeeLeaveWorkflow {

    enum Status {
        PENDING, APPROVED, REJECTED
    }

    static abstract class Employee {
        private final String name;

        Employee(String name) {
            this.name = name;
        }

        public abstract boolean isLeaveAllowed(long days);

        public LeaveRequest submitLeave(LocalDate start, LocalDate end) {
            return new LeaveRequest(this, start, end);
        }
    }

    static class FullTimeEmployee extends Employee {
        FullTimeEmployee(String name) {
            super(name);
        }

        @Override
        public boolean isLeaveAllowed(long days) {
            return days <= 20;
        }
    }

    static class PartTimeEmployee extends Employee {
        PartTimeEmployee(String name) {
            super(name);
        }

        @Override
        public boolean isLeaveAllowed(long days) {
            return days <= 10;
        }
    }

    static class Contractor extends Employee {
        Contractor(String name) {
            super(name);
        }

        @Override
        public boolean isLeaveAllowed(long days) {
            return days <= 5;
        }
    }

    static class Reviewer {
        private final String name;

        Reviewer(String name) {
            this.name = name;
        }

        public void review(LeaveRequest request, boolean approve) {
            request.completeReview(this, approve);
        }
    }

    static class LeaveRequest {
        private final Employee employee;
        private final LocalDate start;
        private final LocalDate end;
        private Status status = Status.PENDING;
        private Reviewer reviewedBy;

        private LeaveRequest(Employee employee,
                             LocalDate start, LocalDate end) {
            if (start == null || end == null || end.isBefore(start)) {
                throw new IllegalArgumentException("Invalid leave dates.");
            }

            this.employee = employee;
            this.start = start;
            this.end = end;

            System.out.printf(
                    "Leave request submitted for %s (%s to %s). "
                            + "Status: Pending.%n",
                    employee.name, start, end);
        }

        public long getDays() {
            // Both the start date and end date count as leave days.
            return ChronoUnit.DAYS.between(start, end) + 1;
        }

        private String statusText() {
            switch (status) {
                case APPROVED:
                    return "Approved";
                case REJECTED:
                    return "Rejected";
                default:
                    return "Pending";
            }
        }

        private void completeReview(Reviewer reviewer, boolean approve) {
            if (status != Status.PENDING) {
                System.out.println(
                        "Cannot review an already decided request.");
                return;
            }

            reviewedBy = reviewer;

            boolean policyAllows = employee.isLeaveAllowed(getDays());

            if (approve && policyAllows) {
                status = Status.APPROVED;
            } else {
                status = Status.REJECTED;
            }

            System.out.printf(
                    "%s reviewed %s's leave request. Status: %s.%n",
                    reviewedBy.name, employee.name, statusText());

            if (approve && !policyAllows) {
                System.out.println("Reason: Leave exceeds the policy limit.");
            }
        }

        public void requestPending() {
            if (status != Status.PENDING) {
                System.out.println("Cannot change leave request status from "
                        + statusText() + " to Pending.");
                return;
            }

            System.out.println("Leave request is already Pending.");
        }
    }

    public static void main(String[] args) {
        Employee john = new FullTimeEmployee("John");
        Employee jane = new PartTimeEmployee("Jane");

        Reviewer alice = new Reviewer("Alice");
        Reviewer bob = new Reviewer("Bob");

        LeaveRequest johnRequest = john.submitLeave(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 5));

        alice.review(johnRequest, true);

        LeaveRequest janeRequest = jane.submitLeave(
                LocalDate.of(2026, 2, 10),
                LocalDate.of(2026, 2, 11));

        bob.review(janeRequest, false);

        johnRequest.requestPending();
    }
}