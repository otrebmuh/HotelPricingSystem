package com.hps.common.domain.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * DateRange value object that represents a range of dates.
 * Immutable by design.
 */
public final class DateRange implements Iterable<LocalDate> {
    private final LocalDate startDate;
    private final LocalDate endDate;
    
    private DateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public static DateRange of(LocalDate startDate, LocalDate endDate) {
        return new DateRange(startDate, endDate);
    }
    
    public static DateRange of(LocalDate startDate, int numberOfDays) {
        return new DateRange(startDate, startDate.plusDays(numberOfDays - 1));
    }
    
    public static DateRange singleDay(LocalDate date) {
        return new DateRange(date, date);
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public int getNumberOfDays() {
        return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }
    
    public boolean contains(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
    
    public boolean overlaps(DateRange other) {
        return !(other.endDate.isBefore(this.startDate) || other.startDate.isAfter(this.endDate));
    }
    
    public DateRange intersection(DateRange other) {
        if (!overlaps(other)) {
            throw new IllegalArgumentException("Date ranges do not overlap");
        }
        
        LocalDate newStartDate = startDate.isAfter(other.startDate) ? startDate : other.startDate;
        LocalDate newEndDate = endDate.isBefore(other.endDate) ? endDate : other.endDate;
        
        return new DateRange(newStartDate, newEndDate);
    }
    
    public List<LocalDate> toList() {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            dates.add(current);
            current = current.plusDays(1);
        }
        return dates;
    }
    
    @Override
    public Iterator<LocalDate> iterator() {
        return toList().iterator();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateRange dateRange = (DateRange) o;
        return startDate.equals(dateRange.startDate) && 
               endDate.equals(dateRange.endDate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }
    
    @Override
    public String toString() {
        if (startDate.equals(endDate)) {
            return startDate.toString();
        }
        return startDate + " to " + endDate;
    }
} 