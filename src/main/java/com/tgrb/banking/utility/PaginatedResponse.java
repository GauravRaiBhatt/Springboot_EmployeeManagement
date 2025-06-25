package com.tgrb.banking.utility;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse<T> {
    private int pageNumber;
    private int pageSize;
    private List<T> content;
    private int totalNoOfPages;
    private boolean isLastPage;
    private long totalNoOfRecords;
    private int noOfRecordsInThisPage;
}
