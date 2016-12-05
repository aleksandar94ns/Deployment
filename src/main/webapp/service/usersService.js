app.service('usersService', function($http){
    return {
        getMe: function(onSuccess, onError){
            $http.get('/api/users/me').then(onSuccess, onError);
        },
        listGuests: function(onSuccess, onError){
            $http.get('/api/users/guests').then(onSuccess, onError);
        },
        changePassword: function(credentials, onSuccess, onError){
            $http.patch('/api/users/me/changePassword', credentials).then(onSuccess, onError);
        },
        edit: function(user, onSuccess, onError){
            $http.patch('/api/users/me', user).then(onSuccess, onError);
        }
    }
});