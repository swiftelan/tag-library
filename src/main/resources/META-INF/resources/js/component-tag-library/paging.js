+function($) {
	'use strict';
	var Paging = function(element, settings) {
		this.settings = $.extend(true, {}, Paging.settings, settings);
		this.$element = $(element);
		this.$element.on('click', 'a[href]', $.proxy(this.handlePageClick, this));
		this.$element.on(this.getNamespacedEvent('content-load'), $.proxy(this.replace, this));
	};

	Paging.settings = {
		namespace : 'component-tag-library.paging'
	};

	Paging.prototype.getNamespacedEvent = function(event) {
		return event + '.' + this.settings.namespace;
	};

	Paging.prototype.handlePageClick = function(event) {
		event.preventDefault();
		var $target = $(event.target);
		var xhr = $.get($target.attr('href'));
		xhr.done($.proxy(function(response) {
			var responseHTML = [ $.parseHTML(response) ];
			this.$element.trigger(this.getNamespacedEvent('content-load'), responseHTML);
			var tableNamespace = this.settings.table.data('comp.table').getNamespacedEvent('content-load');
			this.settings.table.trigger(tableNamespace, responseHTML);
		}, this));
	};

	Paging.prototype.replace = function(event, response) {
		var $response = $(response);
		var $newContent = $response.find('ul[data-jsp-id="' + this.$element.data('jsp-id') + '"]');
		this.$element.html($newContent.html());
	};

	$.fn.paging = function(settings) {
		return this.each(function() {
			var $this = $(this);
			var data = $this.data('comp.paging');
			if (!data) {
				data = new Paging(this, settings);
				$this.data('comp.paging', data);
			}
		});
	};

	$.fn.paging.Constructor = Paging;

}(jQuery);