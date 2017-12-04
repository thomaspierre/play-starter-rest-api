package microDon.models;

import java.time.LocalDate;

public class AggregateUsersTransactionsWrapper {

    private LocalDate startingDate;

    private LocalDate endDate;

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
