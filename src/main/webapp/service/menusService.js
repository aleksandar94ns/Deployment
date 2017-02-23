app.service('menusService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/menus').then(onSuccess, onError);
        },
        create: function (menu, onSuccess, onError) {
            $http.post('/api/menus', menu).then(onSuccess, onError);
        }
    }
});