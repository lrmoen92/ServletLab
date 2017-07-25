package com.astontech.servlet;

import com.astontech.bo.Vehicle;
import com.astontech.bo.VehicleMake;
import com.astontech.bo.VehicleModel;
import com.astontech.dao.VehicleDAO;
import com.astontech.dao.VehicleMakeDAO;
import com.astontech.dao.VehicleModelDAO;
import com.astontech.dao.mysql.VehicleDAOImpl;
import com.astontech.dao.mysql.VehicleMakeDAOImpl;
import com.astontech.dao.mysql.VehicleModelDAOImpl;
import common.helpers.DateHelper;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Logan.Moen on 6/27/2017.
 */
public class VehicleServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(VehicleServlet.class);

    private static VehicleDAO vehicleDAO = new VehicleDAOImpl();
    private static VehicleMakeDAO vehicleMakeDAO = new VehicleMakeDAOImpl();
    private static VehicleModelDAO vehicleModelDAO = new VehicleModelDAOImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        switch(request.getParameter("formName")){
            case("insertVehicle"):
                insertVehicle(request);
                break;
            case("updateVehicle"):
                switch(request.getParameter("updateVehicleButton")) {
                    case("updateVehicle"):
                        updateVehicle(request);
                        break;
                    case("deleteVehicle"):
                        deleteVehicle(request);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }

        request.setAttribute("vehicleList", vehicleDAO.getVehicleList());
        request.setAttribute("vehicleMakeList", vehicleMakeDAO.getVehicleMakeList());
        request.setAttribute("vehicleModelList", vehicleModelDAO.getVehicleModelList());
        request.getRequestDispatcher("./vehicle.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("vehicleList", vehicleDAO.getVehicleList());
        request.setAttribute("vehicleMakeList", vehicleMakeDAO.getVehicleMakeList());
        request.setAttribute("vehicleModelList", vehicleModelDAO.getVehicleModelList());

        request.getRequestDispatcher("./vehicle.jsp").forward(request, response);
    }

    private static void insertVehicle(HttpServletRequest request){
        Vehicle newVehicle = new Vehicle();
        requestToVehicle(request, newVehicle);
        if(vehicleDAO.insertVehicle(newVehicle) > 0)
            request.setAttribute("insertSuccess", "Vehicle Inserted Successfully");
        else
            request.setAttribute("insertSuccess", "Vehicle Not Inserted!");
        vehicleToRequest(request, newVehicle);

    }

    private static void updateVehicle(HttpServletRequest request){
        Vehicle newVehicle = new Vehicle();
        requestToVehicle(request, newVehicle);
        if(vehicleDAO.updateVehicle(newVehicle)) {
            request.setAttribute("updateSuccess", "Success!");
        } else {
            request.setAttribute("updateSuccess", "Failure!");
        }
    }

    private static void deleteVehicle(HttpServletRequest request){
        Vehicle newVehicle = new Vehicle();
        requestToVehicle(request, newVehicle);
        if(vehicleDAO.deleteVehicle(newVehicle.getVehicleId())) {
            request.setAttribute("deleteSuccess", "Success!");
        } else {
            request.setAttribute("deleteSuccess", "Failure!");
        }
    }

    private static void vehicleToRequest(HttpServletRequest request, Vehicle vehicle){

        request.setAttribute("vehicleId", vehicle.getVehicleId());
        request.setAttribute("plate", vehicle.getLicensePlate());
        request.setAttribute("vin", vehicle.getVin());
        request.setAttribute("year", vehicle.getYear());
        request.setAttribute("color", vehicle.getColor());
        request.setAttribute("selectModel", vehicle.getVehicleModelId());
        VehicleModel vehicleModel = vehicleModelDAO.getVehicleModelById(vehicle.getVehicleModelId());
        VehicleMake vehicleMake = vehicleMakeDAO.getVehicleMakeById(vehicleModel.getVehicleMakeId());
        request.setAttribute("selectMake", vehicleMake.getVehicleMakeId());
    }

    private static void requestToVehicle(HttpServletRequest request, Vehicle vehicle){

        if(!(request.getParameter("vehicleId").equals("")))
            vehicle.setVehicleId(Integer.parseInt(request.getParameter("vehicleId")));

        vehicle.setLicensePlate(request.getParameter("plate"));
        vehicle.setVin(request.getParameter("vin"));
        vehicle.setYear(Integer.parseInt(request.getParameter("year")));
        vehicle.setColor(request.getParameter("color"));
        vehicle.setVehicleModelId(Integer.parseInt(request.getParameter("selectModel")));

        //region BULLSHIT

        vehicle.setIsPurchase(true);
        vehicle.setPurchasePrice(0);
        vehicle.setPurchaseDate(DateHelper.stringToUtilDate("1990-1-20", "yyyy-MM-dd"));
        //endregion



    }
}
