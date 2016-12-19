app.service('restaurantsService', function($http){
    return {
        listVisited: function(onSuccess, onError){
            $http.get('/api/restaurants/me').then(onSuccess, onError);
        },
        list: function(onSuccess, onError){
            $http.get('/api/restaurants').then(onSuccess, onError);
        },
        create: function (restaurant, onSuccess, onError) {
            $http.post('/api/restaurants', restaurant).then(onSuccess, onError);
        }
    }
});