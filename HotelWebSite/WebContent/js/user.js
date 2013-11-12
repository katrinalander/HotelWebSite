app.controller('Controller',function($scope,dataFactory,$rootScope,$location,$cookieStore){

	$scope.icon1="img/default.png";
	$scope.icon2="img/default.png";
	$scope.icon3="img/default.png";
	$scope.icon4="img/default.png";
	$scope.icon5="img/default.png";
	$scope.icon6="img/default.png";
	$scope.icon7="img/default.png";
	$scope.myResultVisible=false;

	
	$scope.checkForm=function(field, type){
		 var pp = '';
		// validate login
		   if(type == 'login'){
		   var pp = /^[a-zA-Z][a-zA-Z-\._]+[a-zA-Z0-9]$/;
		   }
		// only A-Z
		   if(type == 'en'){
		   var pp = /^[a-zA-Z ]*$/;
		   }
		// A-Z and 1,2,3
		   else if(type == 'en123'){
		   var pp = /^[a-zA-Z0-9]*$/;
		   }
		// !email
		   else if(type == 'email'){
		   var pp = /^[a-zA-Z][-\._a-zA-Z0-9]+@(?:[a-zA-Z0-9][-a-zA-Z0-9]+\.)+[a-zA-Z]{2,6}$/;
		   }
		// only 123
		   else if(type == '123'){
		   var pp = /^[0-9]*$/;
		   }
		//if empty
		   else if(pp == ''){
		   return false;
		   }
		// not empty
		      if (field === ''){
		         return false;
		      }
		//validation about type
		    else if(!field.match(pp)){
		    return false;
		    }
		      return true;   
	};

	$scope.validFName=function(){
		if($scope.fname==undefined){
			$scope.fName="control-group error";
			$scope.messFN=" please input your first name";
			$scope.icon1="img/attention.png";
		}
		else if($scope.checkForm($scope.fname,'en')){
			$scope.fName="control-group success";
			$scope.messFN="";
			$scope.icon1="img/success.png";
		}
		else{
			$scope.fName="control-group error";
			$scope.messFN="only A-Z!";
			$scope.icon1="img/attention.png";
		}
	};//validFName
	$scope.validLName=function(){
		$scope.validFName();
		if($scope.lname==undefined){
			$scope.lName="control-group error";
			$scope.messLN=" please input your last name";
			$scope.icon2="img/attention.png";
		}
		else if($scope.checkForm($scope.lname,'en')){
			$scope.lName="control-group success";
			$scope.messLN="";
			$scope.icon2="img/success.png";
		}
		else{
			$scope.lName="control-group error";
			$scope.messLN="only A-Z!";
			$scope.icon2="img/attention.png";
		}
	};//validLName
	$scope.validEmail=function(){
		$scope.validLName();
		if($scope.email==undefined){
			$scope.fieldEmail="control-group error";
			$scope.messEmail=" please input your email";
			$scope.icon3="img/attention.png";
		}
		else if($scope.checkForm($scope.email,'email')){
			$scope.fieldEmail="control-group success";
			$scope.messEmail="";
			$scope.icon3="img/success.png";
		}
		else{
			$scope.fieldEmail="control-group error";
			$scope.messEmail="only 0-9!";
			$scope.icon3="img/attention.png";
		}
	};//validEmail
	$scope.validAddress=function(){
		$scope.validEmail();
		if($scope.addr1==undefined && $scope.sity==undefined && $scope.state==undefined && $scope.zip==undefined && 
				$scope.country==undefined && $scope.phone==undefined){
			$scope.fieldAddress="control-group error";
			$scope.messAddress=" please fill all fields of address";
			$scope.icon4="img/attention.png";
		}
		else if($scope.checkForm($scope.sity,'en') && $scope.checkForm($scope.state,'en') && $scope.checkForm($scope.zip,'123')
				&& $scope.checkForm($scope.country,'en')){
			$scope.fieldAddress="control-group success";
			$scope.messAddress="";
			$scope.icon4="img/success.png";
		}
		else{
			$scope.fieldAddress="control-group error";
			$scope.messAddress=" please fill correct all fields of address";
			$scope.icon4="img/attention.png";
		}
	};//valid address
	$scope.validPhone=function(){
		$scope.validAddress();
		if ($scope.phone==undefined){
			$scope.fieldPhone="control-group error";
			$scope.messPhone=" please input your phone number";
			$scope.icon5="img/attention.png";
		}
		else if($scope.checkForm($scope.phone,'123')){
			$scope.fieldPhone="control-group success";
			$scope.messPhone="";
			$scope.icon5="img/success.png";
		}
		else{
			$scope.fieldPhone="control-group error";
			$scope.messPhone=" please input your phone correctly";
			$scope.icon5="img/attention.png";
		}
	};//validPhone
	
	$scope.validPassword=function(){
		$scope.validPhone();
		if ($scope.password==undefined){
			$scope.fieldPassword="control-group error";
			$scope.messPassword=" please input only a-z & 0-9!";
			$scope.icon6="img/attention.png";
		}
		if($scope.checkForm($scope.password,'en123')){
			$scope.fieldPassword="control-group success";
			$scope.messPassword="";
			$scope.icon6="img/success.png";
		}
		else{
			$scope.fieldPassword="control-group error";
			$scope.messPassword=" please input only a-z & 0-9!";
			$scope.icon6="img/attention.png";
		}
	};//validPassword
	$scope.validConfirm=function(){
		$scope.validPassword();
		if($scope.confpassword === $scope.password){
			$scope.fieldConfpassword="control-group success";
			$scope.messConfpassword="";
			$scope.icon7="img/success.png";
		}
		else{
			$scope.fieldConfpassword="control-group error";
			$scope.messConfpassword="you must input same password";
			$scope.icon7="img/attention.png";
		}
	};//validPassword
		
	$scope.addUser = function() {
		var userStr = {
				  "user_name":$scope.fname+' '+$scope.lname,
				  "email":$scope.email,
				  "address":$scope.addr1+' '+' '+$scope.sity+' '+$scope.state+' '+$scope.zip+' '+$scope.country,
				  "phone":$scope.phone,
				  "password":$scope.password
		  };
		userStr = JSON.stringify(userStr);
//-----------------Data Factory--------------------------------------------------
		dataFactory.addUserFactory(userStr)
		.success(function(status){
			if(status){
				$cookieStore.put("email",$scope.email);
				$cookieStore.put("password",$scope.password);
			$location.path('/cabinet');}
			else{
				alert("You already regestred on our site please input login and passwor");
				$location.path('/login');
			}
		})
		.error(function(error){
		alert("I have error!!!");
		});
	}; 
	
	$scope.buttonOK=function(){
		$scope.indicator = true;
		if($scope.checkForm($scope.fname,'en') && $scope.checkForm($scope.lname,'en') && $scope.checkForm($scope.email,'email')
				&& $scope.checkForm($scope.sity,'en') && $scope.checkForm($scope.state,'en') && $scope.checkForm($scope.zip,'123')
				&& $scope.checkForm($scope.country,'en') && $scope.checkForm($scope.phone,'123') && $scope.checkForm($scope.password,'en123')
				&& ($scope.confpassword === $scope.password)){
			
			$scope.addUser();
//			$scope.createEmployee('/ExampleBootstrap/NewServlet',$scope.takeData);
//			$scope.myResultVisible=true;
		
		}
		else{
			alert("You must to complete all fields of this form!");
		}
	};
});