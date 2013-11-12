app.controller('dealCtrl',function($scope,dataFactory,$cookieStore,$filter,$location){
	var toDate1 = new Date();
	toDate1.setHours(0,0,0,0);
	toDate1.setDate(toDate1.getDate() + 1);
	$scope.checkIn = new Date(toDate1);
	var toDate2 = new Date();
	toDate2.setHours(0,0,0,0);
	toDate2.setDate(toDate2.getDate() + 4); // var x = 1 (number of day)
	$scope.checkOut = new Date(toDate2);
	
	dataFactory.getDealFactory()
	.success(function(dealrooms){
		if(dealrooms===null){
			$scope.showDeals = true;
		}//if
		else{
			$scope.rooms = dealrooms;
			$scope.showDeals = false;
		}
	})
	.error(function(error){
		console.log("I have error: "+error);
	});
	
	$scope.reserveData = function(id){
		$scope.type=$scope.rooms[id].type;
		$scope.total=($scope.rooms[id].price_deal*3);
		if($scope.rooms[id].Id == "1"){$cookieStore. put('sngl','1');$cookieStore. put('dbl','0');$cookieStore. put('twin','0');$cookieStore. put('trpl','0');$cookieStore. put('king','0');$cookieStore. put('hmn','0');roomType="sngl";}
		if($scope.rooms[id].Id == "2"){$cookieStore. put('sngl','0');$cookieStore. put('dbl','1');$cookieStore. put('twin','0');$cookieStore. put('trpl','0');$cookieStore. put('king','0');$cookieStore. put('hmn','0');roomType="dbl";}
		if($scope.rooms[id].Id == "3"){$cookieStore. put('sngl','0');$cookieStore. put('dbl','0');$cookieStore. put('twin','1');$cookieStore. put('trpl','0');$cookieStore. put('king','0');$cookieStore. put('hmn','0');roomType="twin";}
		if($scope.rooms[id].Id == "4"){$cookieStore. put('sngl','0');$cookieStore. put('dbl','0');$cookieStore. put('twin','0');$cookieStore. put('trpl','1');$cookieStore. put('king','0');$cookieStore. put('hmn','0');roomType="trpl";}
		if($scope.rooms[id].Id == "5"){$cookieStore. put('sngl','0');$cookieStore. put('dbl','0');$cookieStore. put('twin','0');$cookieStore. put('trpl','0');$cookieStore. put('king','1');$cookieStore. put('hmn','0');roomType="king";}
		if($scope.rooms[id].Id == "6"){$cookieStore. put('sngl','0');$cookieStore. put('dbl','0');$cookieStore. put('twin','0');$cookieStore. put('trpl','0');$cookieStore. put('king','0');$cookieStore. put('hmn','1');roomType="hmn";}
	};
	
	$scope.addReserv = function(){
		$scope.indicator=true;
		if ($cookieStore.get('email') === "" ){console.log("in if addreserv");
			$location.path('/login');
		}//if
		else{console.log("in else");
		var reservStr = {
				  "email":$cookieStore.get('email'),
				  "checkIn":$filter('date')($scope.checkIn, 'yyyy-MM-dd'),
				  "checkOut":$filter('date')($scope.checkOut, 'yyyy-MM-dd'),
				  "price":$scope.total,
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
});