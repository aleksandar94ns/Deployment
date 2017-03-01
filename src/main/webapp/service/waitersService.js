app.service('waitersService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/users/waiters').then(onSuccess, onError);
        },
        edit: function (waiter, onSuccess, onError) {
            $http.patch('/api/bids', waiter).then(onSuccess, onError);
        },
        create: function (waiter, onSuccess, onError) {
            $http.post('/api/users/waiters', waiter).then(onSuccess, onError);
        }
    }
});
