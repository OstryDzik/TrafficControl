Server:

	- traffic

		+ get_traffic: po to strzela modul swiatel

		+ set_traffic: po to strzela modul symulacji

	- lights:

		+ get_lights: po to strzela modul symulacji

		+ set_lights: po strzela modul swiatel

	- mode:

		+ get_mode: zwraca auto lub manual; odpowiednie moduly reaguja

		+ set_mode: ustawienie trybu na manual/auto, informacja o czasie

	- gui:

		+ kill_module: info do serwera, ze symulacja/swiatla padly

		+ add_car: dodanie samochodu

		+ set_interval: ustaw okres pojawiania sie samochodow

Swiatla:

	- jesli get_traffic nie odpowie po 300ms to przechodza w tryb domyslnego algorytmu

	- w trybie manual pyta sie za pomoca shall_i czy moze leciec dalej

Symulacja:

	- jesli: get_ligths nie odpowie po 300ms to symulacja sama sobie ustala zmiane swiatel

	- w trybie manual pyta sie za pomoca shall_i czy moze leciec dalej

GUI:

	- pyta sie o get_lights, get_traffic

	- wysyla:

		+ next_tick

		+ set_mode

		+ kill_module
