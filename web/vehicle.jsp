<%--
  Created by IntelliJ IDEA.
  User: Logan.Moen
  Date: 6/27/2017
  Time: 1:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="./static/css/site.css" />
    <title>Vehicle Management</title>
</head>
    <div class="border">
        <form name="insertVehicle" action="./vehicle" method="post">
            <input type="hidden" name="formName" value="insertVehicle">
            <input type="hidden" name="vehicleId" value="${vehicleId}" />
            <div class="insertDiv">
            <select name="selectMake" id="selectMake">
                <option value="0">(Select Make)</option>
                <c:forEach var="vehicleMake" items="${vehicleMakeList}">
                    <c:choose>
                        <c:when test="${vehicleMake.vehicleMakeId == selectMake}">
                            <option class="make" selected value="${vehicleMake.vehicleMakeId}">${vehicleMake.vehicleName}</option>
                        </c:when>
                        <c:otherwise>
                            <option class="make" value="${vehicleMake.vehicleMakeId}">${vehicleMake.vehicleName}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
            </div>
            <div  class="insertDiv">
            <select name="selectModel" id="selectModel">
                <option value="0">(Select Model)</option>
                <c:forEach var="vehicleModel" items="${vehicleModelList}">
                    <c:choose>
                        <c:when test="${vehicleModel.vehicleModelId == selectModel}">
                            <option class="model" id="${vehicleModel.vehicleModelId}" selected value="${vehicleModel.vehicleModelId}">${vehicleModel.vehicleModelName}</option>
                        </c:when>
                        <c:otherwise>
                            <option class="model" id="${vehicleModel.vehicleModelId}" value="${vehicleModel.vehicleModelId}">${vehicleModel.vehicleModelName}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
            </div>
            <div  class="insertDiv">
                Plate:
                <input class="insertInput" type="text" name="plate" value="${plate}" placeholder="License Plate">
            </div>
            <div  class="insertDiv">
                VIN:
                <input class="insertInput" type="text" name="vin" value="${vin}" placeholder="VIN">
            </div>
            <div  class="insertDiv">
                Year:
                <input class="insertInput" type="text" name="year" value="${year}" placeholder="Year">
            </div>
            <div  class="insertDiv">
                Color:
                <input class="insertInput" type="text" name="color" value="${color}" placeholder="Color">
            </div>
            <div  class="insertDiv">
            <button type="submit">
                Add Vehicle
            </button>
            </div>
            ${insertSuccess}
        </form>
    </div>
    <div class="borderBig">
            <table id="updateTable">
                <tr>
                    <td>
                        Plate
                    </td>
                    <td>
                        VIN
                    </td>
                    <td>
                        Year
                    </td>
                    <td>
                        Color
                    </td>
                    <td>
                        Make
                    </td>
                    <td>
                        Model
                    </td>
                    <td>
                        ${updateSuccess}
                    </td>
                    <td>
                        ${deleteSuccess}
                    </td>
                </tr>
                <tr>
                    <td colspan="8"><hr></td>
                </tr>

                <c:forEach var="vehicle" items="${vehicleList}">
                <form name="updateVehicle" action="./vehicle" method="post">
                <input type="hidden" name="formName" value="updateVehicle">
                <input type="hidden" name="vehicleId" value="${vehicle.vehicleId}">
                <tr>
                    <td class="plateTd">
                        <input class="plateInput" type="text" name="plate" value="${vehicle.licensePlate}" placeholder="License Plate">
                    </td>
                    <td class="vinTd">
                        <input class="vinInput" type="text" name="vin" value="${vehicle.vin}" placeholder="VIN">
                    </td>
                    <td class="yearTd">
                        <input class="yearInput" type="text" name="year" value="${vehicle.year}" placeholder="Year">
                    </td>
                    <td class="colorTd">
                        <input class="colorInput" type="text" name="color" value="${vehicle.color}" placeholder="Color">
                    </td>
                    <td class="makeTd">
                        <select class="makeSelect" name="selectMake">
                            <c:forEach var="vehicleModel" items="${vehicleModelList}">
                                <c:choose>
                                    <c:when test="${vehicle.vehicleModelId == vehicleModel.vehicleModelId}">
                                        <c:forEach var="vehicleMake" items="${vehicleMakeList}">
                                            <c:choose>
                                                <c:when test="${vehicleModel.vehicleMakeId == vehicleMake.vehicleMakeId}">
                                                    <option selected value="${vehicleMake.vehicleMakeId}">
                                                            ${vehicleMake.vehicleName}
                                                    </option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${vehicleMake.vehicleMakeId}">
                                                            ${vehicleMake.vehicleName}
                                                    </option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="modelTd">
                        <select class="modelSelect" name="selectModel">
                            <c:forEach var="vehicleModel" items="${vehicleModelList}">
                                <c:choose>
                                    <c:when test="${vehicle.vehicleModelId == vehicleModel.vehicleModelId}">
                                        <option selected value="${vehicleModel.vehicleModelId}">
                                                ${vehicleModel.vehicleModelName}
                                        </option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${vehicleModel.vehicleModelId}">
                                                ${vehicleModel.vehicleModelName}
                                        </option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="updateButtonTd">
                        <button type="submit" name="updateVehicleButton" value="updateVehicle">Update</button>
                    </td>
                    <td class="deleteButtonTd">
                        <button type="submit" name="updateVehicleButton" value="deleteVehicle">Delete</button>
                    </td>
                </tr>
                </form>
                </c:forEach>

            </table>

    </div>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <%--<script src="./static/js/site.js"></script>--%>
</body>
</html>
