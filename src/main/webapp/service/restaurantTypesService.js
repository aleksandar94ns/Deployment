app.service('restaurantTypesService', function($http){
    return {
        list: function(onSuccess, onError) {
            $http.get('/api/restaurantTypes').then(onSuccess, onError);
        },
        create: function (restaurantType, onSuccess, onError) {
            $http.post('/api/restaurantTypes', restaurantType).then(onSuccess, onError);
        }
    }
});