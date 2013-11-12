var app = angular.module('myApp',['ui.bootstrap', 'ngCookies']);

app.config(function($routeProvider){
	$routeProvider.
		when('/',{templateUrl:'/HotelWebSite/partials/Welcome.html',controller:'viewCtrl'}).
		when('/home',{templateUrl:'/HotelWebSite/partials/Welcome.html',controller:'viewCtrl'}).
		when('/view',{templateUrl:'/HotelWebSite/partials/view.html',controller:'viewCtrl'}).
		when('/room/:id',{templateUrl:'/HotelWebSite/partials/suite.html',controller:'roomCtrl'}).
		when('/new',{templateUrl:'/HotelWebSite/partials/NewClient.html',controller:'Controller'}).
		when('/login',{templateUrl:'/HotelWebSite/partials/login.html',controller:'loginCtrl'}).
		when('/search',{templateUrl:'/HotelWebSite/partials/search.html',controller:'searchCtrl'}).
		when('/reserv',{templateUrl:'/HotelWebSite/partials/Reservation.html',controller:'reservationCtrl'}).
		when('/feedback',{templateUrl:'/HotelWebSite/partials/Feedback.html',controller:'feedbackCtrl'}).
		when('/deal',{templateUrl:'/HotelWebSite/partials/deal.html',controller:'dealCtrl'}).
		when('/cabinet',{templateUrl:'/HotelWebSite/partials/cabinet.html',controller:'cabCtrl'}).
		otherwise({redirecTo:'/',template:'/HotelWebSite/partials/Welcome.html',controller:'viewCtrl'});
});

function roomCtrl ($scope,$timeout,$routeParams,dataFactory,$location,$rootScope,$cookieStore,$filter){
	$scope.alerts = [ ];
	$scope.room=$scope.rooms[$routeParams.id];
	$scope.price=$scope.room.price_regular;
	var roomType;
			if($scope.room.Id == "1"){$cookieStore. put('sngl','1');$cookieStore. put('dbl','0');$cookieStore. put('twin','0');$cookieStore. put('trpl','0');$cookieStore. put('king','0');$cookieStore. put('hmn','0');roomType="sngl";}
			if($scope.room.Id == "2"){$cookieStore. put('sngl','0');$cookieStore. put('dbl','1');$cookieStore. put('twin','0');$cookieStore. put('trpl','0');$cookieStore. put('king','0');$cookieStore. put('hmn','0');roomType="dbl";}
			if($scope.room.Id == "3"){$cookieStore. put('sngl','0');$cookieStore. put('dbl','0');$cookieStore. put('twin','1');$cookieStore. put('trpl','0');$cookieStore. put('king','0');$cookieStore. put('hmn','0');roomType="twin";}
			if($scope.room.Id == "4"){$cookieStore. put('sngl','0');$cookieStore. put('dbl','0');$cookieStore. put('twin','0');$cookieStore. put('trpl','1');$cookieStore. put('king','0');$cookieStore. put('hmn','0');roomType="trpl";}
			if($scope.room.Id == "5"){$cookieStore. put('sngl','0');$cookieStore. put('dbl','0');$cookieStore. put('twin','0');$cookieStore. put('trpl','0');$cookieStore. put('king','1');$cookieStore. put('hmn','0');roomType="king";}
			if($scope.room.Id == "6"){$cookieStore. put('sngl','0');$cookieStore. put('dbl','0');$cookieStore. put('twin','0');$cookieStore. put('trpl','0');$cookieStore. put('king','0');$cookieStore. put('hmn','1');roomType="hmn";}

//---------Datepiker------------------------------------
	$scope.today = function() {
	    $scope.dt1 = new Date();
	  };
	 	  
	  $scope.today();
	  $scope.dt = new Date();//min date for check-in datepicker
	$scope.after = function(){
		var toDate = new Date();
		$scope.dt2 = new Date(toDate);
	};
		$scope.after();
				
	  $scope.showWeeks = false;
	  
//	  $scope.clear1 = function () {
//	    $scope.dt1 = null;
//	    $scope.price=$scope.room.price_regular;
//	  };
//	  $scope.clear2 = function () {
//		    $scope.dt2 = null;
//		    $scope.price=$scope.room.price_regular;
//	  };
	  var dat=[];
	  dataFactory.allDatesFactory(roomType)//send roomType to dataFactory - AllDateServlet
	  .success(function(dates){
		  dat = dates;
	  })//success
	  .error(function(error){
		  $scope.alertShow = true;
		  $scope.alerts.push({type:'error',msg: "ERROR\n in getting dates: \n"+error});
		  return false;
	  });//error
	  
	  // Disable date selection
//	  
//	  $scope.disabled = function(date, mode) {//this function for disabled dates in calendar in suite.html
//		  angular.forEach(dat, function(value){
//			for(var i=0; i<dat.length;i++){ 
//				  console.log("in loop");
//				  var d = $filter('date')(dat[i],'dd');
//				  var m = $filter('date')(dat[i],'MM');
//				  var y = $filter('date')(dat[i],'yyyy');
//				  console.log("date="+d+" m="+m+" y="+y);
//				  console.log("dat="+dat[i]);
////				  if(!$scope.disabledDate(date,mode)){
//				 var isDisable = ( mode === 'day' && ( date.getDate() === d && date.getMonth() === m && date.getFullYear()=== y) );
//				if(isDisable){break;}
//				  }}
////				  var disdate = ( mode === 'day' && ( date.getDate() === d && date.getMonth() === m && date.getFullYear()=== y) );
////				  console.log("disdate="+disdate);
////				  return disdate;
//			  }; //for each 
//		  return isDisable;
//		  };
//		  
//		  $scope.disabledDate = function(date,mode,d,m,y){
////		  var d = 11; var y = 2013; var m = 11;
//		  console.log("d="+d+" m="+m+" y="+y);
//			  return ( mode === 'day' && ( date.getDate() === d && date.getMonth() === m && date.getFullYear()=== y) );
//		  };
		  
//	  $scope.open1 = function() {
//	    $timeout(function() {
//	      $scope.opened1 = true;
//	    });
//	  };
//	  $scope.open2 = function() {
//		    $timeout(function() {
//		      $scope.opened2 = true;
//		    });
//		  };
	  $scope.buttonDate=function(){
		  if ($scope.dt1!=null && $scope.dt2!=null && ($scope.dt2-$scope.dt1)>0){
		  $scope.price = ($scope.room.price_regular*($scope.countDay($scope.dt1,$scope.dt2))).toFixed(2);
//		  -----------------------------------ROOTSCOPE DATA OF RESERVATION----------------------------------
		  $rootScope.allData={
					  "room_type":$scope.room.type,
					  "checkIn":$scope.dt1,
					  "checkOut":$scope.dt2,
					  "price":$scope.price
			  };
		  $scope.total=$scope.price;
		  $scope.checkIn = $scope.dt1;
		  $scope.checkOut = $scope.dt2;
		  $cookieStore.put('room_type',$scope.room.type);
		  $cookieStore.put('checkIn',$scope.dt1);
		  $cookieStore.put('checkOut',$scope.dt2);
		  $cookieStore.put('price',$scope.price);
//		----------------------------------------------------------------------------------------------------
		  }
		  else{$scope.alertShow = true;
		  		$scope.alerts.push({type:'error',msg: "You must to choose both dates"});}
		  var status = true;
		  for(var i=0; i<dat.length;i++){
		  var d = $filter('date')(dat[i],'yyyy-MM-dd');
		  var d1 = $filter('date')($scope.dt1,'yyyy-MM-dd');
		  var d2 = $filter('date')($scope.dt2,'yyyy-MM-dd');
		  console.log("d="+d+" d1="+d1);
		  if((d1<d)&&(d2>d)){status=false;}
		  else if((d1 === d)||(d2 === d)){
				  status=false; 
			  }//if
		  }//for
		  if(!status){
			  $scope.alertShow = true;
			  $cookieStore.remove('room_type');
			  $cookieStore.remove('checkIn');
			  $cookieStore.remove('checkOut');
			  $cookieStore.remove('price');
			  $scope.alerts.push({type:'error',msg: "This type room not available to this dates."});
		  }
	  };
	  $scope.closeAlert = function(index) {
		    $scope.alerts.splice(index, 1);
		    $scope.alertShow = false;
		  };
		  
	  
	  $scope.reservDate=function(){
		  $scope.buttonDate();
		  if($cookieStore.get('email') === undefined){
			  $location.path('/login');}
//				var valid = {
//						  "email":$scope.email,
//						  "password":$scope.password}
//		  dataFactory.validLogin(valid).
//		  success(function (result) {$location.path('/login');}).
//		  error(function(result){alert("I can't!!!!");});}
		  else{
			  $cookieStore.put('room_type',$scope.room.type);
			  $cookieStore.put('checkIn',$scope.dt1);
			  $cookieStore.put('checkOut',$scope.dt2);
			  $cookieStore.put('price',$scope.price);
		  }
		  
	  };
	  
//	  $scope.reservCtrl = function(){
//		  $scope.email=$cookieStore.get('email');
//			$scope.room_type=$cookieStore.get('room_type');
//			$scope.checkIn=$cookieStore.get('checkIn');
//			$scope.checkOut=$cookieStore.get('checkOut');
//			$scope.price=$cookieStore.get('price');
//	  };
			
		$scope.addReserv = function() {
			$scope.indicator=true;
			if ($cookieStore.get('email') === "" ){console.log("in if addreserv");
				$location.path('/login');
			}//if
			else{console.log("in else");
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
			dataFactory.addReservFactory(reservStr)
			.success(function(){
				$location.path('/cabinet');
			})
			.error(function(error){
				$scope.alertShow = true;
				$scope.alerts.push({type:'error',msg: "I have error!!!"});
			});
			}//else
		};

	  $scope.countDay=function(d1, d2){
		  var days = Math.round((d2-d1)/86400000);
		  return days;
		  };
		  
	  $scope.dateOptions = {
	    'year-format': "'yy'",
	    'starting-day': 1
	  };
//----------------------carousel for pictures----------------------------------------------
	  var slides = $scope.slides = [];
	  $scope.addSlide = function() {
	    slides.push({
	      image: ['/pic-hotel/' + roomType + '/' +'bedroom.jpg','/pic-hotel/' + roomType + '/' +'bathroom.jpg','/pic-hotel/' + roomType + '/' +'bedroom2.jpg','/pic-hotel/' + roomType + '/' +'patio.jpg'][slides.length % 4],
	      text: ['bedroom','bathroom','bedroom','patio'][slides.length % 4]
	    });
	  };
	  for (var i=0; i<4; i++) {
	    $scope.addSlide();
	  }
	  
	};
//-------------------------------------------------------------------------------
app.controller('viewCtrl', function($scope,$http,$rootScope,$cookieStore,dataFactory){
	if($cookieStore.get('email')===undefined){
		$scope.client_name = "guest";
	}
	else{
		$scope.emailShow=true;
		dataFactory.getUserDataFactory($cookieStore.get('email'))
		.success(function(user){
			$scope.client_name = user.user_name;
		})
		.error(function(error){
			alert(error);
		});
	}
	$scope.rooms={};
	$scope.email=$cookieStore.get('email');
	dataFactory.getRoomFactory()
		.success(function(suites){
			$scope.rooms=suites;})
		.error(function(error){
			console.log("ERROR in getting rooms: "+error);
		});
});


app.controller('loginCtrl',function($scope,$location,dataFactory,$rootScope,$cookieStore,$route){
	$scope.alerts = [ ];
	if($cookieStore.get('email')!==undefined){$scope.loginShow =true;}
		$scope.validCtrl=function(){//function valid
			var valid = {
					  "email":$scope.email,
					  "password":$scope.password,
			  };
			valid = JSON.stringify(valid);
			dataFactory.validLogin(valid)
			.success(function(status){
				if(status){
					$rootScope.user={
							email:$scope.email,
							password:$scope.password
					};
					$scope.loginShow = true;
					$cookieStore.put('email',$rootScope.user.email);
					$cookieStore.put('password',$rootScope.user.password);
					$scope.email = $cookieStore.get('email');
					$scope.password = $cookieStore.get('password');
					$route.reload();
					$location.path('/login');
					if($rootScope.allData === undefined){
						$route.reload();
						$location.path('/cabinet');
					}
					else{$location.path('/');
						$route.reload();
						$location.path('/cabinet'); }
				}
				else {$scope.alerts.push({type:'error',msg: "You must to do registration in our system"});
					}
					})
			.error(function(error){
				alert("I have error!!!");
			});
		};
//	}//if
//	else{
//		$scope.validCtrl();
//		$scope.email = $cookieStore.get('email');
//		$scope.password = $cookieStore.get('password');
//		$scope.logoutShow = true;
//	}//else
	$scope.logoutCtrl=function(){
		$scope.loginShow = false;
		$cookieStore.remove('email');
		$cookieStore.remove('password');
		$scope.email='';
		$scope.password='';
		$location.path('/');
		$route.reload();
		$location.path('/login');
	};//logoutCtrl
	 $scope.closeAlert = function(index) {
		    $scope.alerts.splice(index, 1);
		    $scope.alertShow = false;
		  };
});
