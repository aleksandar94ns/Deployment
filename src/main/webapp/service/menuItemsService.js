app.service('menuItemsService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/menuItems').then(onSuccess, onError);
        },
        create: function (menuItem, onSuccess, onError) {
            $http.post('/api/menuItems', menuItem).then(onSuccess, onError);
        }
    }
});
