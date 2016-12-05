app.service('usersService', function($http){
    return {
        listGuests: function(onSuccess, onError){
            $http.get('/api/users/guests').then(onSuccess, onError);
        },
        changePassword: function(credentials, onSuccess, onError){
            $http.patch('/api/users/me/changePassword', credentials).then(onSuccess, onError);
        }
    }
});