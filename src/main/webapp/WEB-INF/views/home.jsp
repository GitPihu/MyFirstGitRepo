<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body ng-app="myApp" ng-controller="myController">
	<form name="userform">
		<div class="table-responsive">
			<table class="table table-bordered" style="width: 600px">
				
				<tr>
					<td>Name</td>
					<td><input type="text" name="name" ng-model="name" required>
						<span ng-show="userform.name.$touched && userform.name.$invalid">Name
							required!!</span></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><input type="email" name="emaildId" ng-model="email"
						required>
						<span
						ng-show="userform.emaildId.$touched && userform.emaildId.$invalid">Email
							required!!</span>
						</td>
					
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="userPassword"
						ng-model="userPassword" required />
						<span
						ng-show="userform.userPassword.$touched && userform.userPassword.$invalid">Password required!!</span>
						
						</td>
					
				</tr>

				<tr>
					<td colspan="2"><input type="button" ng-disabled="userform.name.$touched && userform.name.$invalid"
						class="btn btn-primary btn-sm" ng-click="loginData()"
						value="Login" /></td>
				</tr>
		
			</table>
		</div>
	</form>
	
</body>
</html>
