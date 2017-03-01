app.controller('CreateEmployeeShiftController', function ($scope, $http, $state, $mdDialog, shiftsService, chefsService, bartendersService, waitersService, employeeShiftsService, authenticationService) {

    $scope.employees = [];
    $scope.shifts = [];
    $scope.chefs = [];
    $scope.waiters = [];
    $scope.bartenders = [];

    $scope.manager = authenticationService.getUser();

    shiftsService.list(function (response) {
        $scope.shifts = response.data;
        chefsService.list(function (response) {
            $scope.chefs = response.data;
            waitersService.list(function (response) {
                $scope.waiters = response.data;
                bartendersService.list(function (response) {
                    $scope.bartenders = response.data;
                    $scope.chefs.forEach(function (chef) {
                        $scope.employees.push(chef);
                    });
                    $scope.waiters.forEach(function (waiter) {
                        $scope.employees.push(waiter);
                    });
                    $scope.bartenders.forEach(function (bartender) {
                        $scope.employees.push(bartender);
                    });
                });
            });
        });
    });

    $scope.queryEmployees = function (searchText) {
        var queryResults = [];
        for (var i = 0; i < $scope.employees.length; i++) {
            if ($scope.employees[i].firstName.toLowerCase().match(searchText.toLowerCase())) {
                queryResults.push($scope.employees[i]);
            }
        }
        return queryResults;
    };

    $scope.queryShifts = function (searchShift) {
        var queryResults = [];
        for (var i = 0; i < $scope.shifts.length; i++) {
            if ($scope.shifts[i].name.toLowerCase().match(searchShift.toLowerCase())) {
                queryResults.push($scope.shifts[i]);
            }
        }
        return queryResults;
    };

    $scope.submit = function () {
        employeeShiftsService.create($scope.employeeShift, function () {
            $mdDialog.hide();
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});