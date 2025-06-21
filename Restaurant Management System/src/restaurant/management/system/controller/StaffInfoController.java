/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import restaurant.management.system.view.StaffInfoView;
import restaurant.management.system.model.StaffData;
import restaurant.management.system.dao.StaffDao;
import java.util.List;

/**
 *
 * @author samee
 */
public class StaffInfoController {
    private StaffInfoView staffInfoView;
    private StaffDao staffDao = new StaffDao();

    public StaffInfoController(StaffInfoView view) {
        this.staffInfoView = view;
        loadAndDisplayStaff();
    }

    private void loadAndDisplayStaff() {
        List<StaffData> staffList = staffDao.getApprovedStaff(); // Only approved staff
        staffInfoView.displayStaff(staffList, this);
    }

    public void open() {
        staffInfoView.setVisible(true);
    }

    public void close() {
        staffInfoView.dispose();
    }

    public void refreshStaffDisplay() {
        loadAndDisplayStaff();
    }
}