app.controller('AreasController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, areasService) {

    $scope.page.current = 14;

    var loadData = function () {
        areasService.list(function (response) {
            $scope.areas = response.data;
        });
    };

    loadData();

    $scope.addArea = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createArea.html',
            controller: 'CreateAreaController'
        }).finally(function () {
            loadData();
        });
    };

    $scope.arrangeTables = function (area) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/editTableArrangement.html',
            controller: 'EditTableArrangementController',
            locals: { area: area }
        }).finally(function () {
            loadData();
        });
    };
});