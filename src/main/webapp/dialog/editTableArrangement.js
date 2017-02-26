app.controller('EditTableArrangementController', function ($rootScope, $scope, $http, $state, $mdDialog, areasService, restaurantTablesService, area) {

    areasService.listByRestaurant(area.restaurant.id, function(response) {
        var removeIndex = response.data.map(function(item) { return item.id; }).indexOf(area.id);
        response.data.splice(removeIndex, 1);
        response.data.unshift(area);
        $rootScope.$broadcast('SetAreas', response.data);
    });

    $scope.$on('AllTables', function (event, data) {
        $scope.restaurantTables = data;
    });

    $scope.addColumn = function () {
        $rootScope.$broadcast('AddColumn', {});
    };

    $scope.addRow = function () {
        $rootScope.$broadcast('AddRow', {});
    };

    $scope.submit = function () {
        restaurantTablesService.update(area, $scope.restaurantTables, function() {
            $mdDialog.hide();
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});