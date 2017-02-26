app.service('areasService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/areas').then(onSuccess, onError);
        },
        listByRestaurant: function(restaurant, onSuccess, onError){
            $http.get('/api/areas/restaurant/' + restaurant).then(onSuccess, onError);
        },
        listByRestaurantAndAvailability: function(restaurant, arrivalDate, departureDate,  onSuccess, onError) {
            $http.get('/api/areas/available/?restaurant=' + restaurant + '&arrivalDate=' + arrivalDate + '&departureDate=' + departureDate).then(onSuccess, onError);
        },
        create: function (area, onSuccess, onError) {
            $http.post('/api/areas', area).then(onSuccess, onError);
        }
    }
});
