app.service('orderItemsService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/orderItems').then(onSuccess, onError);
        }
    }
});