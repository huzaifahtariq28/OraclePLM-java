require([ "config/config" ], function() {
	require([ "binder", "globals" ], function(loader) {
		var navigationHandler = function() {
			Sammy(function() {
				// Catch all possible patterns of hash.
				this.get(/\#(.*)/, function() {
					var allParams = this.params.splat[0].split("/");
					var url = allParams[0].toLowerCase();
					switch (url) {
					case "items":
						g_baseviewmodel.showItemsPage();
						break;
					case "attachments":
						g_baseviewmodel.showAttachmentsPage();
						break;
					case "bom":
						g_baseviewmodel.showBOMPage();
						break;
					case "aml":
						g_baseviewmodel.showAMLPage();
						break;
					case "inventorydata":
						g_baseviewmodel.showInventoryDataPage();
						break;
					default:
						alert("The URL doesn't exist.");
					}
				});

				// If no #route is given then route to main page
				this.get("", function(url) {
					if (g_baseviewmodel)
						g_baseviewmodel.showItemsPage();
				});
			}).run();
		};

		loader.loadBaseView(false, navigationHandler);
	});
});
