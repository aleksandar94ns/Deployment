app.service('restaurantTablesService', function($http){
    return {
        create: function (restaurantTable, onSuccess, onError) {
            $http.post('/api/restaurantTables', restaurantTable).then(onSuccess, onError);
        },
        update: function (area, restaurantTables, onSuccess, onError) {
            $http.put('/api/restaurantTables/' + area.id, restaurantTables).then(onSuccess, onError);
        }
    }
});