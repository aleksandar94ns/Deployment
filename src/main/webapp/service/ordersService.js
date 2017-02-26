app.service('ordersService', function($http){
    return {
        create: function (order, onSuccess, onError) {
            $http.post('/api/orders', order).then(onSuccess, onError);
        }
    }
});