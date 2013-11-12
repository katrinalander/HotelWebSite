angular.module('myApp')
.factory('dataFactory', ['$http', function($http) {

	var dataFactory = {};
	dataFactory.getRoomFactory = function(){
		return $http.post ('/HotelWebSite/AllRoomServlet');
	};
	
	dataFactory.validLogin = function(valid){
		return $http.post('/HotelWebSite/ValidLoginServlet',valid);
	};

	dataFactory.addReservFactory = function(reservStr) {
    	return $http.post('/HotelWebSite/ReservationServlet', reservStr);
    };
	
	dataFactory.addUserFactory = function(userStr){
		return $http.post('/HotelWebSite/AddUserServlet',userStr);
	};
	
	dataFactory.allDatesFactory = function(roomType){
		return $http.post('/HotelWebSite/AllDateServlet',roomType);
	};
	
	dataFactory.searchFactory = function(searchStr){
		return $http.post('/HotelWebSite/SearchServlet',searchStr);
	};
	
	dataFactory.getReservationsFactory = function(email){
		return $http.post('/HotelWebSite/getReservationsServlet',email);
	};
	
	dataFactory.delReservationFactory = function(reservation){
		return $http.post('/HotelWebSite/DeleteReservationServlet',reservation);
	};
	
	dataFactory.getUserDataFactory = function(email){
		return $http.post('/HotelWebSite/GetUserServlet',email);
	};
	
	dataFactory.updateUserFactory = function(userStr){
		return $http.post('/HotelWebSite/UpdateUserServlet',userStr);
	};
	
	dataFactory.deleteUserFactory = function(mail){
		return $http.post('/HotelWebSite/DeleteUserServlet',mail);
	};
	
	dataFactory.allFeedbackFactory = function(){
		return $http.post('/HotelWebSite/FeedbackServlet');
	};
	
	dataFactory.putFeedbackFactory = function(feedback){
		return $http.post('/HotelWebSite/CreateFeedbackServlet',feedback);
	};
	
	dataFactory.deleteFeedbackFactory = function(feedId){
		return $http.post('/HotelWebSite/DeleteFeedbackServlet',feedId);
	};
	dataFactory.getDealFactory = function(){
		return $http.post('/HotelWebSite/GetDealServlet');
	};
	
	return dataFactory;
}]);