app.service('sellersService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/users/sellers').then(onSuccess, onError);
        },
        create: function (seller, onSuccess, onError) {
            $http.post('/api/users/sellers', seller).then(onSuccess, onError);
        }
    }
});