app.controller('reservationCtrl',function($scope,dataFactory,$location,$rootScope,$filter,$cookieStore){
	if($rootScope.user === undefined){$location.path('/');}
	else{ 
		$scope.email=$cookieStore.get('email');
		$scope.room_type=$cookieStore.get('room_type');
		$scope.checkIn=$cookieStore.get('checkIn');
		$scope.checkOut=$cookieStore.get('checkOut');
		$scope.price=$cookieStore.get('price');
		
	$scope.addReserv = function() {
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
		console.log("reservStr="+reservStr);
		dataFactory.addReservFactory(reservStr)
		.success(function(){
			$location.path('/cabinet');
		})
		.error(function(error){
		alert("I have error!!!");
		});
		alert("reservStr"+reservStr);
	}
	}//else
});