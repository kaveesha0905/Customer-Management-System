package com.assignment.customer_management.service;

import com.assignment.customer_management.model.Customer;
import com.assignment.customer_management.repository.CustomerRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Date;

@Service
public class ExcelService {

    @Autowired
    private CustomerRepository repository;

    public void save(MultipartFile file) {
        try {
            InputStream is = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<Customer> customers = new ArrayList<>();

            if (rows.hasNext()) rows.next(); // Header skip (Name, DOB, NIC)

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (currentRow == null || isRowEmpty(currentRow)) continue;

                Customer customer = new Customer();

                // 1. Name
                Cell nameCell = currentRow.getCell(0);
                customer.setName(nameCell != null ? nameCell.getStringCellValue() : "Unknown");

                // 2. DOB (මෙතනයි ප්‍රශ්නය තියෙන්නේ)
                Cell dobCell = currentRow.getCell(1);
                if (dobCell != null && dobCell.getCellType() != CellType.BLANK) {
                    if (dobCell.getCellType() == CellType.NUMERIC) {
                        customer.setDob(dobCell.getDateCellValue());
                    } else {
                        try {
                            customer.setDob(java.sql.Date.valueOf(dobCell.getStringCellValue()));
                        } catch (Exception e) {
                            customer.setDob(new Date()); // Format වැරදි නම් අද දිනය දානවා
                        }
                    }
                } else {
                    // DOB එක null නම් default දිනයක් දානවා (Error එක නවත්වන්න)
                    customer.setDob(new Date(0)); // 1970-01-01
                }

                // 3. NIC
                Cell nicCell = currentRow.getCell(2);
                if (nicCell != null) {
                    if (nicCell.getCellType() == CellType.NUMERIC) {
                        customer.setNic(String.valueOf((long) nicCell.getNumericCellValue()));
                    } else {
                        customer.setNic(nicCell.getStringCellValue());
                    }
                }

                customers.add(customer);

                // Batch save - ලක්ෂ ගණන් දත්ත නිසා 5000න් 5000ට save කරනවා
                if (customers.size() >= 5000) {
                    repository.saveAll(customers);
                    customers.clear();
                }
            }

            if (!customers.isEmpty()) {
                repository.saveAll(customers);
            }
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException("Excel error: " + e.getMessage());
        }
    }

    private boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK) return false;
        }
        return true;
    }
}