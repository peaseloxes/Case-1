<html>
<body>
<h2>Jersey RESTful Web Application!</h2>

<p><a href="api/courses">Courses</a>
    for more information on Jersey!

<h3>Courses</h3>
<form enctype="multipart/form-data" method="post" action="api/courses/create">
    <input type="file" name="file">
    <input type="submit">
</form>

<br/><br/><br/><br/><h3>Students</h3>

<form enctype="multipart/form-data" method="post" action="api/students/create">
    <%--
    @FormDataParam("courseInstanceId") final String courseInstanceId,
    @FormDataParam("firstName") final String firstName,
            @FormDataParam("lastName") final String lastName,
            @FormDataParam("email") final String email,
            @FormDataParam("companyName") final String companyName,
            @FormDataParam("bidNumber") final String bidNumber,
            @FormDataParam("accountNumber") final String accountNumber,
            @FormDataParam("phoneNumber") final String phoneNumber,
            @FormDataParam("department") final String department,
            @FormDataParam("streetNumber") final String streetNumber,
            @FormDataParam("streetName") final String streetName,
            @FormDataParam("postalCode") final String postalCode,
            @FormDataParam("city") final String city

    --%>
        <label for="courseInstanceId">courseInstanceId</label> <input type="text" id="courseInstanceId" name="courseInstanceId"><br/>
    <label for="firstName">first name</label> <input type="text" id="firstName" name="firstName"><br/>
        <label for="lastName">last name</label> <input type="text" id="lastName" name="lastName"><br/>
        <%--<label for="email">email</label> <input type="text" id="email" name="email"><br/>--%>
        <label for="companyName">company name</label> <input type="text" id="companyName" name="companyName"><br/>
        <label for="bidNumber">bid number</label> <input type="text" id="bidNumber" name="bidNumber"><br/>
        <%--<label for="phoneNumber">phone number</label> <input type="text" id="phoneNumber" name="phoneNumber"><br/>--%>
        <label for="department">department</label> <input type="text" id="department" name="department"><br/>
        <%--<label for="streetNumber">streetnumber</label> <input type="text" id="streetNumber" name="streetNumber"><br/>--%>
        <%--<label for="streetName">street name</label> <input type="text" id="streetName" name="streetName"><br/>--%>
        <%--<label for="postalCode">postal code</label> <input type="text" id="postalCode" name="postalCode"><br/>--%>
        <%--<label for="city">city</label> <input type="text" id="city" name="city"><br/>--%>
    <input type="submit">
</form>

</body>
</html>
