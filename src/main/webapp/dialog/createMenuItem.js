app.controller('CreateMenuItemController', function ($scope, $http, $state, $mdDialog, menusService, menuItemsService, chefsService) {

    menusService.list(function(response) {
        $scope.menus = response.data;
    });

    $scope.queryMenus = function (searchText) {
        var queryResults = [];
        for (var i = 0; i < $scope.menus.length; i++) {
            if ($scope.menus[i].name.toLowerCase().match(searchText.toLowerCase())) {
                queryResults.push($scope.menus[i]);
            }
        }
        return queryResults;
    };

    chefsService.list(function(response) {
        $scope.chefs = response.data;
    });

    $scope.specialities = null;

    $scope.queryChefs = function (searchSpec) {
        var queryResults = [];
        for (var i = 0; i < $scope.chefs.length; i++) {
            if ($scope.chefs[i].speciality.toLowerCase().match(searchSpec.toLowerCase())) {
                //queryResults.push($scope.chefs[i]);
                $scope.specialities = $scope.chefs[i].speciality;
                queryResults.push($scope.specialities);
            }
        }
        return queryResults;
    };

    $scope.submit = function () {
        menuItemsService.create($scope.menuItem, function () {
            $mdDialog.hide();
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});