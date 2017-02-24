app.service('restaurantTypesService', function($http){
    return {
        list: function(onSuccess, onError) {
            $http.get('/api/restaurantTypes').then(onSuccess, onError);
        }
    }
});