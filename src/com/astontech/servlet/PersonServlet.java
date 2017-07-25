package com.astontech.servlet;

import com.astontech.bo.Person;
import com.astontech.dao.PersonDAO;
import com.astontech.dao.mysql.PersonDAOImpl;
import common.helpers.DateHelper;
//import common.helpers.ServletHelper;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Logan.Moen on 6/26/2017.
 */
public class PersonServlet extends javax.servlet.http.HttpServlet {

    final static Logger logger = Logger.getLogger(PersonServlet.class);

    private static PersonDAO personDAO = new PersonDAOImpl();

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        switch(request.getParameter("formName")){
            case "choosePerson":
                choosePerson(request);
                break;

            case "updatePerson":
                updatePerson(request);
                break;

            default:
                break;
        }

        request.setAttribute("personList", personDAO.getPersonList());
        request.getRequestDispatcher("./person.jsp").forward(request, response);

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setAttribute("personList", personDAO.getPersonList());
        request.setAttribute("selectPerson", generatePersonDropDownHTML(0));
        request.getRequestDispatcher("./person.jsp").forward(request, response);
    }

    private static void choosePerson(HttpServletRequest request) {
        logger.info("Form #1 - Form Name=" + request.getParameter("formName"));

//        ServletHelper.logRequestParams(request);

        //notes:  everything from the request comes back as a string
        String selectedPersonId = request.getParameter("selectPerson");
        if(Integer.parseInt(selectedPersonId) == 0) {
                request.setAttribute("selectPersonError", "Please Select A Person!");
                request.setAttribute("selectPerson", generatePersonDropDownHTML(0));
            } else {
                Person selectedPerson = personDAO.getPersonById(Integer.parseInt(selectedPersonId));
                logger.info("Selected Person Details: " + selectedPerson.toString());
                personToRequest(request, selectedPerson);
                request.setAttribute("selectPerson", generatePersonDropDownHTML(selectedPerson.getPersonId()));

        }


    }

    private static void updatePerson(HttpServletRequest request) {
        logger.info("Form #2 - Form Name=" + request.getParameter("formName"));

//        ServletHelper.logRequestParams(request);

        Person updatePerson = new Person();
        requestToPerson(request, updatePerson);
        logger.info(updatePerson.toString());

        if(personDAO.updatePerson(updatePerson)){
            request.setAttribute("updateSuccessful", "Person Updated Successfully!");
        } else {
            request.setAttribute("updateSuccessful", "Person Not Updated!!!");
        }

        updatePerson = personDAO.getPersonById(updatePerson.getPersonId());
        personToRequest(request, updatePerson);

        request.setAttribute("selectPerson", generatePersonDropDownHTML(Integer.parseInt(request.getParameter("personId"))));

    }

    private static String generatePersonDropDownHTML(int selectedPersonId) {
        /*
            <select name="selectPerson">
                <option value="1">Dan</option>
                <option value="2">James</option>
                <option value="3">Adrian</option>
                <option value="4">Sean</option>
            </select>
         */
        StringBuilder strBld = new StringBuilder();
        strBld.append("<select name=\"selectPerson\">");
        strBld.append("<option value=\"0\">Select A Person</option>");
        for(Person p : personDAO.getPersonList()){
            if(p.getPersonId() == selectedPersonId){
                strBld.append("<option selected value=\"").append(p.getPersonId()).append("\">").append(p.getFullName()).append("</option>");
            } else {
                strBld.append("<option value=\"").append(p.getPersonId()).append("\">").append(p.getFullName()).append("</option>");
            }
        }
        strBld.append("</select>");
        return strBld.toString();

    }

    private static void requestToPerson(HttpServletRequest request, Person person){
        //notes:  persist a value
        person.setPersonId(Integer.parseInt(request.getParameter("personId")));
        person.setFirstName(request.getParameter("firstName"));
        person.setMiddleName(request.getParameter("middleName"));
        person.setLastName(request.getParameter("lastName"));
        person.setBirthDate(DateHelper.stringToUtilDate(request.getParameter("birthDate"), "yyyy-MM-dd"));
        person.setSSN(request.getParameter("socialSecurityNumber"));

        //region BULLSHIT
        person.setTitle("");
        person.setCreateDate(DateHelper.stringToUtilDate("1992-05-21", "yyyy-MM-dd"));
        person.setDisplayFirstName("");
        person.setIsDeleted(false);
        person.setGender("");
        //endregion

    }

    private static void personToRequest(HttpServletRequest request, Person person){
        request.setAttribute("personId", person.getPersonId());
        request.setAttribute("firstName", person.getFirstName());
        request.setAttribute("middleName", person.getMiddleName());
        request.setAttribute("lastName", person.getLastName());
        request.setAttribute("birthDate", person.getBirthDate());
        request.setAttribute("socialSecurityNumber", person.getSSN());
    }

}
