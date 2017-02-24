app.service('chefsService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/users/chefs').then(onSuccess, onError);
        },
        create: function (chef, onSuccess, onError) {
            $http.post('/api/users/chefs', chef).then(onSuccess, onError);
        }
    }
});