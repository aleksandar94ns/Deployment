app.service('areasService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/areas').then(onSuccess, onError);
        }
    }
});
