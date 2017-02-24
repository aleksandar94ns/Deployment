app.service('bartendersService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/users/bartenders').then(onSuccess, onError);
        },
        create: function (bartender, onSuccess, onError) {
            $http.post('/api/users/bartenders', bartender).then(onSuccess, onError);
        }
    }
});
