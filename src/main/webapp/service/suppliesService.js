app.service('suppliesService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/supplies').then(onSuccess, onError);
        },
        create: function (supply, onSuccess, onError) {
            $http.post('/api/supplies', supply).then(onSuccess, onError);
        }
    }
});