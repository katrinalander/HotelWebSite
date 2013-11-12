app.controller('feedbackCtrl',function ($scope,dataFactory,$route,$location,$cookieStore,$filter) {
  $scope.rate = 9;
  $scope.max = 10;
  $scope.isReadonly = false;

  $scope.hoveringOver = function(value) {
    $scope.overStar = value;
    $scope.percent = 100 * (value / $scope.max);
  };
  
 

  $scope.ratingStates = [
    {stateOn: 'icon-ok-sign', stateOff: 'icon-ok-circle'},
    {stateOn: 'icon-star', stateOff: 'icon-star-empty'},
    {stateOn: 'icon-heart', stateOff: 'icon-ban-circle'},
    {stateOn: 'icon-heart'},
    {stateOff: 'icon-off'}];
//--------show messages from database------------------------------------- 
  	dataFactory.allFeedbackFactory()
  	.success(function(feedback){
  		var feed;$scope.fedbs=[];
  		angular.forEach(feedback,function(value){
  			console.log("value.email: "+value.email+" $cookieStore.get('email')"+$cookieStore.get('email'));
  	  		if(value.email===$cookieStore.get('email')){
  	  			feed={"id":value.id,
  	  				  "name":value.name,
  	  				  "message":value.message,
  	  				  "date":value.date,
  	  				  "raiting":value.raiting,
  	  				  "status":true};console.log("status="+feed.name);
  	  		}
  	  		else{
  	  		feed={"id":value.id,
  	  			"name":value.name,
	  			"message":value.message,
	  			"date":value.date,
	  			"raiting":value.raiting,
	  			"status":false};console.log("status="+feed.status);
  	  		}
  	  		$scope.fedbs.push(feed);
  		});
  	})
  	.error(function(error){
  		console.log("I have error! "+error);
  	});
//-----------------------------------------------------------------------------------
  	$scope.feedback = function(){
		$scope.indicator = true;
  		if($cookieStore.get('email') === "" ){
  			$location.path('/login');
  		}//if
  		else{
  			dataFactory.getUserDataFactory($cookieStore.get('email'))
  			.success(function(user){
//  				var client = user;
  				console.log("user="+user);
  				$scope.uname = user.user_name;
  				var date = $filter('date')(new Date(),'yyyy-MM-dd');
  				var feedback = {
  						"name":$scope.uname,
  						"raiting":($scope.rate*10),
  						"message":$scope.message,
  						"email":$cookieStore.get('email'),
  						"date":date
  				};
  				feedback = JSON.stringify(feedback);
  				dataFactory.putFeedbackFactory(feedback)
  				.success(function(){
  					$route.reload();
					$location.path('/feedback');
  				});
  			})//success factory getNameFactory
  			.error(function(error){
  				console.log("I have an error "+error);
  			});
  		}//else
  	};
  	
  	$scope.deleteFeed = function(id){
  		var feedId = $scope.fedbs[id].id;
  		dataFactory.deleteFeedbackFactory(feedId)
  		.success(function(){
  			$route.reload();
			$location.path('/feedback');
  		})
  		.error(function(error){
  			console.log("I have an error "+error);
  		});
  	};//deleteFeed
});