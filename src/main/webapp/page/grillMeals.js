app.controller('GrillMealsController', GrillMealsController);

function GrillMealsController($timeout){

    this.infiniteItems = {
        numLoaded_: 0,
        toLoad_: 0,


        getItemAtIndex: function(index) {
            if (index > this.numLoaded_) {
                this.fetchMoreItems_(index);
                return null;
            }

            return index;
        },

        getLength: function() {
            return this.numLoaded_ + 5;
        },

        fetchMoreItems_: function(index) {

            if (this.toLoad_ < index) {
                this.toLoad_ += 20;
                $timeout(angular.noop, 300).then(angular.bind(this, function() {
                    this.numLoaded_ = this.toLoad_;
                }));
            }
        }
    };
};