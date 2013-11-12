app.controller('cabCtrl',function($scope,$cookieStore,$filter,dataFactory,$rootScope,$location,$route){
	$scope.icon1="img/default.png";
		$scope.icon2="img/default.png";
		$scope.icon3="img/default.png";
		$scope.icon4="img/default.png";
		$scope.icon5="img/default.png";
		$scope.icon6="img/default.png";
		$scope.icon7="img/default.png";	

		$scope.email=$cookieStore.get('email');
		$scope.room_type=$cookieStore.get('room_type');
		$scope.checkIn=$cookieStore.get('checkIn');
		$scope.checkOut=$cookieStore.get('checkOut');
		$scope.price=$cookieStore.get('price');
		if(!($cookieStore.get('room_type') == undefined)){
		$scope.cancelVisio = true;}
		else{
			$scope.cancelVisio = false;}
		
		var email = JSON.stringify($scope.email);
//----------------Data Factory------------------------------
		dataFactory.getReservationsFactory(email)
			.success(function(reservations){
				$scope.reserve = reservations;
			})
			.error(function(error){
				alert("I have error!!!");
			});
		
		$scope.addReserv = function() {
			$scope.indicator = true;
			var reservStr = {
					  "email":$cookieStore.get('email'),
					  "checkIn":$filter('date')($cookieStore.get('checkIn'), 'yyyy-MM-dd'),
					  "checkOut":$filter('date')($cookieStore.get('checkOut'), 'yyyy-MM-dd'),
					  "price":$cookieStore.get('price'),
					  "sngl":$cookieStore.get('sngl'),
					  "dbl":$cookieStore.get('dbl'),
					  "twin":$cookieStore.get('twin'),
					  "trpl":$cookieStore.get('trpl'),
					  "king":$cookieStore.get('king'),
					  "hmn":$cookieStore.get('hmn')
			};
			resrvStr = JSON.stringify(reservStr);
//-----------------------Data Factory------------------------------------
			dataFactory.addReservFactory(reservStr)
			.success(function(){
				$scope.cancelVisio=false;
				$cookieStore.remove('room_type');
				$cookieStore.remove('checkIn');
				$cookieStore.remove('checkOut');
				$cookieStore.remove('price');
				$cookieStore.type={sngl:"",dbl:"",twin:"",trpl:"",king:"",hmn:""};
				$route.reload();
				$location.path('/cabinet');
			})
			.error(function(error){
			alert("I have error!!! \n"+error);
			});
		};//addreserv
		
		$scope.cancelReserve = function(){
			$scope.cancelVisio=false;
			$cookieStore.put('room_type',null);
			$cookieStore.put('checkIn',null);
			$cookieStore.put('checkOut',null);
			$cookieStore.put('price',null);
			$cookieStore.type={sngl:"",dbl:"",twin:"",trpl:"",king:"",hmn:""};
		};//cancelReserve
		
		$scope.getData = function(id){
			$rootScope.delReserv={
					id:$scope.reserve[id].reservId,
					type:$scope.reserve[id].type,
					checkIn:$scope.reserve[id].checkIn,
					checkOut:$scope.reserve[id].checkOut
					};
		};
		
		$scope.deleteReserve = function(){
			$scope.indicator=true;
			var reservation = {
					"reservId":$rootScope.delReserv.id,
					"type":$rootScope.delReserv.type,
					"checkIn":$rootScope.delReserv.checkIn,
					"checkOut":$rootScope.delReserv.checkOut,
			};
			reservation = JSON.stringify(reservation);
			dataFactory.delReservationFactory(reservation)
			.success(function(){
				$route.reload();
				$location.path('/cabinet');
			})
			.error(function(error){
				alert(error);
			});
		};
//---------------------users data--------------------------------
		$scope.userData = function(){
			dataFactory.getUserDataFactory($cookieStore.get('email'))
			.success(function(user){
				$scope.name = user.user_name;
				$scope.address = user.address;
				$scope.phone = user.phone;
				$scope.nemail = user.email;
			})
			.error(function(error){
				alert(error);
			}); 
		};//userData
		$scope.buttonCancel = function(){
			$scope.userData();
		};
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
			if($scope.name==undefined){
				$scope.fName="control-group error";
				$scope.messFN=" please input your full name";
				$scope.icon1="img/attention.png";
			}
			else if($scope.checkForm($scope.name,'en')){
				$scope.fName="control-group success";
				$scope.messFN="";
				$scope.icon1="img/success.png";
			}
			else{console.log("if 3");
				$scope.fName="control-group error";
				$scope.messFN="only A-Z!";
				$scope.icon1="img/attention.png";
			}
		};//valid full name
		$scope.validEmail=function(){
			$scope.validFName();
			if($scope.email==undefined){
				$scope.fieldEmail="control-group error";
				$scope.messEmail=" please input your email";
				$scope.icon2="img/attention.png";
			}
			else if($scope.checkForm($scope.email,'email')){
				$scope.fieldEmail="control-group success";
				$scope.messEmail="";
				$scope.icon2="img/success.png";
			}
			else{
				$scope.fieldEmail="control-group error";
				$scope.messEmail="only 0-9!";
				$scope.icon2="img/attention.png";
			}
		};//validEmail
		$scope.validAddress=function(){
			$scope.validEmail();
			if($scope.address==undefined){
				$scope.fieldAddress="control-group error";
				$scope.messAddress=" please input your address";
				$scope.icon3="img/attention.png";
			}
			else if($scope.checkForm($scope.email,'email')){
				$scope.fieldAddress="control-group success";
				$scope.messAddress="";
				$scope.icon3="img/success.png";
			}
			else{
				$scope.fieldAddress="control-group error";
				$scope.messAddress="only 0-9!";
				$scope.icon3="img/attention.png";
			}
		};//validAddress
		$scope.validPhone=function(){
			$scope.validAddress();
			if ($scope.phone==undefined){
				$scope.fieldPhone="control-group error";
				$scope.messPhone=" please input your phone number";
				$scope.icon4="img/attention.png";
			}
			else if($scope.checkForm($scope.phone,'123')){
				$scope.fieldPhone="control-group success";
				$scope.messPhone="";
				$scope.icon4="img/success.png";
			}
			else{
				$scope.fieldPhone="control-group error";
				$scope.messPhone=" please input your phone correctly";
				$scope.icon4="img/attention.png";
			}
		};//validPhone
		$scope.validcurPassword = function(){
			$scope.validPhone();
			if ($scope.curpass==undefined){
				$scope.fieldcurPassword="control-group error";
				$scope.messcurPassword=" please input only a-z & 0-9!";
				$scope.icon5="img/attention.png";
			}
			if($scope.checkForm($scope.curpass,'en123') && ($scope.curpass === $cookieStore.get('password'))){
				$scope.fieldcurPassword="control-group success";
				$scope.messcurPassword="";
				$scope.icon5="img/success.png";
			}
			else{
				$scope.fieldcurPassword="control-group error";
				$scope.messcurPassword=" please input your current password";
				$scope.icon5="img/attention.png";
			}
		};
		$scope.validPassword=function(){
			$scope.validcurPassword();
			if ($scope.newpass==undefined){
				$scope.fieldPassword="control-group error";
				$scope.messPassword=" please input only a-z & 0-9!";
				$scope.icon6="img/attention.png";
			}
			if($scope.checkForm($scope.newpass,'en123')){
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
			if($scope.confpass == $scope.newpass){
				$scope.fieldConfpassword="control-group success";
				$scope.messConfpassword="";
				$scope.icon7="img/success.png";
			}
			else{
				$scope.fieldConfpassword="control-group error";
				$scope.messConfpassword="you must input same password";
				$scope.icon7="img/attention.png";
			}
		};//validConfPassword
		$scope.buttonOK = function(){
			$scope.validConfirm();
			if(($scope.confpass == $scope.newpass)&&$scope.checkForm($scope.newpass,'en123')&&($scope.curpass === $cookieStore.get('password'))&&
					$scope.checkForm($scope.phone,'123')&&$scope.checkForm($scope.email,'email')&&$scope.checkForm($scope.name,'en')){
				$scope.indicator = true;
				var userStr = {
						  "user_name":$scope.name,
						  "email":$scope.email,
						  "address":$scope.address,
						  "phone":$scope.phone,
						  "password":$scope.newpass
				  };
				userStr = JSON.stringify(userStr);
//---------------------------Data Factory--------------------------------------------------
				dataFactory.updateUserFactory(userStr)
				.success(function(status){
					if(status){
						$cookieStore.put("email",$scope.email);
						$cookieStore.put("password",$scope.newpass);
						$route.reload();
						$location.path('/cabinet');}
					else{
						alert("We apologise, but we can't update your private data.");
						$location.path('/cabinet');
					}
				})
				.error(function(error){
				alert("I have error!!!");
				});
			}
		};//button ok
		
		$scope.deleteAccount = function(){
			$scope.indicator = true;
			console.log("email="+$cookieStore.get('email'));
			var email = $cookieStore.get('email');
			dataFactory.deleteUserFactory(email)
			.success(function(status){
				if(status){
					$location.path('/');
					$cookieStore.remove("email");
					$cookieStore.remove("password");
				}
				else{
					alert("We can't delete your account, please call to manager.");
				}
			})
			.error(function(error){
				alert("I have an error: \n"+error);
			});
		};
});