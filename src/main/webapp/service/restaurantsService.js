app.service('restaurantsService', function($http){
    return {
        listVisited: function(onSuccess, onError){
            $http.get('/api/restaurants/me').then(onSuccess, onError);
        },
        list: function(onSuccess, onError){
            $http.get('/api/restaurants').then(onSuccess, onError);
        }
    }
});