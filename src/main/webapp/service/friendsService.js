app.service('friendsService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/friends').then(onSuccess, onError);
        },
        listFriends: function(onSuccess, onError){
            $http.get('/api/friends/me').then(onSuccess, onError);
        },
        listPotentialFriends: function (onSuccess, onError) {
            $http.get('/api/friends/potential').then(onSuccess, onError);
        },
        create: function (recipient, onSuccess, onError) {
            $http.post('/api/friends', recipient).then(onSuccess, onError);
        },
        delete: function(id, onSuccess, onError){
            $http.delete('/api/friends/' + id).then(onSuccess, onError);
        },
        patch: function (friendship, onSuccess, onError) {
            $http.patch('/api/friends', friendship).then(onSuccess, onError);
        }
    }
});