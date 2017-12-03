package microDon.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import microDon.clients.models.Account;
import microDon.clients.models.TransactionCategory;
import microDon.converters.LocalDateSerializer;
import microDon.converters.ZoneDateTimeSerializer;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class Transaction {

    private Long id;

    private String description;

    private Double amount;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @JsonProperty("updated_at")
    @JsonSerialize(using = ZoneDateTimeSerializer.class)
    private ZonedDateTime updatedAt;

    @JsonProperty("is_deleted")
    private Boolean isDeleted;

    private TransactionCategory category;

    private Account account;


    public Transaction() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
