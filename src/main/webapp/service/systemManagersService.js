app.service('systemManagersService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/users/systemManagers').then(onSuccess, onError);
        },
        create: function (systemManager, onSuccess, onError) {
            $http.post('/api/users/systemManagers', systemManager).then(onSuccess, onError);
        }
    }
});