app.service('restaurantManagersService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/users/restaurantManagers').then(onSuccess, onError);
        },
        create: function (restaurantManager, onSuccess, onError) {
            $http.post('/api/users/restaurantManagers', restaurantManager).then(onSuccess, onError);
        }
    }
});
