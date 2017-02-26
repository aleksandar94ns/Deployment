app.service('restaurantTablesService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/restaurantTables').then(onSuccess, onError);
        },
        create: function (restaurantTable, onSuccess, onError) {
            $http.post('/api/restaurantTables', restaurantTable).then(onSuccess, onError);
        }
    }
});