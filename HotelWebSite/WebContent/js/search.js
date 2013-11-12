var searchCtrl = function ($scope,$timeout,$rootScope,dataFactory,$routeParams,$location,$cookieStore,$filter,$route) {

	 $scope.countDay=function(d1, d2){
		  var days = Math.round((d2-d1)/86400000);
		  return days;
		  };
	$rootScope.amount={sum:"0"};
	$scope.minDate1 = new Date();
		  
  $scope.today1 = function() {
    $scope.dt1 = new Date();
  };
  $scope.today1();
  
  $scope.today2 = function() {
	    $scope.dt2 = new Date();
	  };
	  $scope.today2();

  $scope.open1 = function() {
    $timeout(function() {
      $scope.opened1 = true;
    });
  };
  $scope.open2 = function() {
	    $timeout(function() {
	      $scope.opened2 = true;
	    });
	  };
  $scope.alerts = [ ];
	  
	  $scope.search = function(){
		  if(($scope.people/3)>$scope.room){
			  $scope.alerts.push({type:'error',msg: "You must to change your parameter (guests & number rooms) in search and try again! For " +
			  		$scope.people+" guests You must take minimum "+(parseInt($scope.people/3)+1)+" rooms"});
			  $scope.searchShow = false;
		  }//if
		  else if($scope.people % $scope.room==0){
			  var r=$scope.people/$scope.room;
			  $scope.searchShow = true;
			  if(r==3){
				  $rootScope.rooms={sngl:"0",dbl:"0",trpl:$scope.room};
			  }//if 3
			  else if(r==2){
				  $rootScope.rooms={sngl:"0",dbl:$scope.room,trpl:"0"};
				  }//else if 2
			  else if(r==1){
				  $rootScope.rooms={sngl:$scope.room,dbl:"0",trpl:"0"};
				  }//else if 1
	  }//else if people % room
		  else if(($scope.people/$scope.room)<2&&($scope.people/$scope.room)>1){
			  $scope.searchShow = true;
		  $rootScope.rooms={sngl:"1",dbl:($scope.room-1),trpl:"0"}; 
	  }//else
		  else if(($scope.people-2)%3==0 && ($scope.people%3!=0)){
			  $scope.searchShow = true;
			  $rootScope.rooms={sngl:"0",dbl:"1",trpl:parseInt(($scope.people-2)/3)};
		  }
		  else if(($scope.people-1)%3==0 && ($scope.people%3!=0)){
			  $scope.searchShow = true;
			  $rootScope.rooms={sngl:"1",dbl:"0",trpl:parseInt(($scope.people-1)/3)};
		  }
		  else{
			  $scope.searchShow = true; console.log("parseInt(($scope.people-1)/2)-1))="+parseInt((($scope.people-1)/2)-1));
			  $rootScope.rooms={sngl:"1",dbl:(parseInt((($scope.people-1)/2)-1)),trpl:"0"}; 
		  }
//		  $scope.alerts.push({msg:"Single="+$rootScope.rooms.sngl+" Double="+$rootScope.rooms.dbl+" Trpl="+$rootScope.rooms.trpl});
		  var searchStr={
				  "checkIn":$scope.dt1,
				  "checkOut":$scope.dt2,
				  "sngl":$rootScope.rooms.sngl,
				  "dbl":$rootScope.rooms.dbl,
				  "trpl":$rootScope.rooms.trpl
		  };
		  searchStr = JSON.stringify(searchStr);
		 var interval = $scope.countDay($scope.dt1,$scope.dt2);
		 $rootScope.iterval={days:interval};
//	------------------------Data Factory-----------------------------------	  
		  dataFactory.searchFactory(searchStr)
		  .success(function(roomslist){
			  $scope.ListOfRooms = roomslist;
			  //calculations of total sum in search
			  if(roomslist.length>1){
				  $scope.total=($scope.ListOfRooms[0].price_regular*$scope.ListOfRooms[0].num+$scope.ListOfRooms[1].price_regular*$scope.ListOfRooms[1].num)*interval;
			  }
			  else if(roomslist.length<1){
				  $scope.alerts.push({type:'error',msg: "We apologize, but at this moment we can't find rooms for You, please call to manager" +
				  		" for assistance, or try to change search parameters."});
				  $scope.searchShow = false;
			  }
			  else{
				  $scope.total=$scope.ListOfRooms[0].price_regular*$scope.ListOfRooms[0].num*interval;
			  }
			  }
			  )
		  .error(function(error){
			  $scope.alerts.push({msg:"factory in error: "+error});
		  });
	  };//search function
	  
	  
	  $scope.closeAlert = function(index) {
		    $scope.alerts.splice(index, 1);
		  };
		  
	$scope.toReserv = function(){
		if(!$cookieStore.get("email")===""){
		$scope.indicator = true;
		$scope.searchShow = false;
		var reservation={
				  "checkIn":$filter('date')($scope.dt1,'yyyy-MM-dd'),
				  "checkOut":$filter('date')($scope.dt2,'yyyy-MM-dd'),
				  "email":$cookieStore.get("email"),
				  "price":$scope.total,
				  "sngl":$rootScope.rooms.sngl,
				  "dbl":$rootScope.rooms.dbl,
				  "trpl":$rootScope.rooms.trpl,
				  "twin":"0",
				  "king":"0",
				  "hmn":"0"
		  };
		  reservation = JSON.stringify(reservation);
		  dataFactory.addReservFactory(reservation)
		  .success(function(){
			  	$route.reload();
		  		$location.path('/cabinet');
		  })
		  .error(function(error){
			  alert(error);
		  });
		}//if login
		else{
			$route.reload();
	  		$location.path('/login');
		}
	};// function toReserv

  $scope.dateOptions = {
    'year-format': "'yy'",
    'starting-day': 1
  };
};