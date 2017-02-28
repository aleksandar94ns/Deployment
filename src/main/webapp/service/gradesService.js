app.service('gradesService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/grades').then(onSuccess, onError);
        }
    }
});