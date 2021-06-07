package core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Patch;
import javax.sound.midi.Synthesizer;

/*********************************************************************
 * Gère des sons en MIDI.<br />
 * <br />
 * Instruments disponibles :
 * <ul>
 * <li>12-str.Gt</li>
 * <li>5th Saw Wave</li>
 * <li>60's E.Piano</li>
 * <li>60's Organ 1</li>
 * <li>808 Tom</li>
 * <li>Accordion Fr</li>
 * <li>Accordion It</li>
 * <li>Acoustic Bs.</li>
 * <li>Agogo</li>
 * <li>Alto Sax</li>
 * <li>AnalogBrass1</li>
 * <li>AnalogBrass2</li>
 * <li>Applause</li>
 * <li>Atmosphere</li>
 * <li>Bagpipe</li>
 * <li>Bandoneon</li>
 * <li>Banjo</li>
 * <li>Baritone Sax</li>
 * <li>Bass & Lead</li>
 * <li>Bassoon</li>
 * <li>Bird</li>
 * <li>Bird 2</li>
 * <li>Bottle Blow</li>
 * <li>Bowed Glass</li>
 * <li>Brass 1</li>
 * <li>Brass 2</li>
 * <li>Breath Noise</li>
 * <li>Brightness</li>
 * <li>Brush</li>
 * <li>Bubble</li>
 * <li>Burst Noise</li>
 * <li>Car-Crash</li>
 * <li>Car-Engine</li>
 * <li>Car-Pass</li>
 * <li>Car-Stop</li>
 * <li>Carillon</li>
 * <li>Castanets</li>
 * <li>Celesta</li>
 * <li>Cello</li>
 * <li>Charang</li>
 * <li>Chiffer Lead</li>
 * <li>Choir Aahs</li>
 * <li>Choir Aahs 2</li>
 * <li>Chorus Gt.</li>
 * <li>Church Bell</li>
 * <li>Church Org.1</li>
 * <li>Church Org.2</li>
 * <li>Church Org.3</li>
 * <li>Clarinet</li>
 * <li>Clav.</li>
 * <li>Clean Gt.</li>
 * <li>Concert BD</li>
 * <li>Contrabass</li>
 * <li>Coupled Hps.</li>
 * <li>Crystal</li>
 * <li>Detuned EP 1</li>
 * <li>Detuned EP 2</li>
 * <li>Detuned Or.1</li>
 * <li>Detuned Or.2</li>
 * <li>DistortionGt</li>
 * <li>Doctor Solo</li>
 * <li>Dog</li>
 * <li>Door</li>
 * <li>DoorCreaking</li>
 * <li>E.Piano 1</li>
 * <li>E.Piano 1v</li>
 * <li>E.Piano 2</li>
 * <li>E.Piano 2v</li>
 * <li>Echo Bell</li>
 * <li>Echo Drops</li>
 * <li>Echo Pan</li>
 * <li>Elec Perc.</li>
 * <li>Electronic</li>
 * <li>English Horn</li>
 * <li>Explosion</li>
 * <li>Fantasia</li>
 * <li>Feedback Gt.</li>
 * <li>Fiddle</li>
 * <li>Fingered Bs.</li>
 * <li>Fl.Key Click</li>
 * <li>Flute</li>
 * <li>Footsteps</li>
 * <li>Fr.Horn 2</li>
 * <li>French Horns</li>
 * <li>Fretless Bs.</li>
 * <li>Funk Gt.</li>
 * <li>Funk Gt.2</li>
 * <li>Glockenspiel</li>
 * <li>Goblin</li>
 * <li>Gt. Feedback</li>
 * <li>Gt.Cut Noise</li>
 * <li>Gt.FretNoise</li>
 * <li>Gt.Harmonics</li>
 * <li>Gun Shot</li>
 * <li>Halo Pad</li>
 * <li>Harmonica</li>
 * <li>Harp</li>
 * <li>Harpsi.o</li>
 * <li>Harpsichord</li>
 * <li>Hawaiian Gt.</li>
 * <li>Heart Beat</li>
 * <li>Helicopter</li>
 * <li>Honky-tonk</li>
 * <li>Horse-Gallop</li>
 * <li>Ice Rain</li>
 * <li>Jazz</li>
 * <li>Jazz Gt.</li>
 * <li>Jetplane</li>
 * <li>Kalimba</li>
 * <li>Koto</li>
 * <li>Lasergun</li>
 * <li>Laughing</li>
 * <li>Machine Gun</li>
 * <li>Mandolin</li>
 * <li>Marimba</li>
 * <li>Melo. Tom 1</li>
 * <li>Melo. Tom 2</li>
 * <li>Metal Pad</li>
 * <li>Music Box</li>
 * <li>Muted Gt.</li>
 * <li>MutedTrumpet</li>
 * <li>Nylon Gt.2</li>
 * <li>Nylon Gt.o</li>
 * <li>Nylon-str.Gt</li>
 * <li>Oboe</li>
 * <li>Ocarina</li>
 * <li>Orchestra</li>
 * <li>OrchestraHit</li>
 * <li>Organ 1</li>
 * <li>Organ 2</li>
 * <li>Organ 3</li>
 * <li>Organ 4</li>
 * <li>Organ 5</li>
 * <li>Overdrive Gt</li>
 * <li>Pan Flute</li>
 * <li>Piano 1</li>
 * <li>Piano 1d</li>
 * <li>Piano 2</li>
 * <li>Piano 3</li>
 * <li>Piccolo</li>
 * <li>Picked Bs.</li>
 * <li>PizzicatoStr</li>
 * <li>Polysynth</li>
 * <li>Power</li>
 * <li>Punch</li>
 * <li>Rain</li>
 * <li>Recorder</li>
 * <li>Reed Organ</li>
 * <li>Reverse Cym.</li>
 * <li>Room</li>
 * <li>Rubber Bass</li>
 * <li>SFX</li>
 * <li>Santur</li>
 * <li>Saw</li>
 * <li>Saw Wave</li>
 * <li>Scratch</li>
 * <li>Screaming</li>
 * <li>Seashore</li>
 * <li>Shakuhachi</li>
 * <li>Shamisen</li>
 * <li>Shanai</li>
 * <li>Sine Wave</li>
 * <li>Siren</li>
 * <li>Sitar</li>
 * <li>Sitar 2</li>
 * <li>Slap Bass 1</li>
 * <li>Slap Bass 2</li>
 * <li>Slow Strings</li>
 * <li>Slow Violin</li>
 * <li>Solo Vox</li>
 * <li>Soprano Sax</li>
 * <li>Soundtrack</li>
 * <li>Space Voice</li>
 * <li>Square</li>
 * <li>Square Wave</li>
 * <li>Standard</li>
 * <li>Star Theme</li>
 * <li>Starship</li>
 * <li>Steel Drums</li>
 * <li>Steel-str.Gt</li>
 * <li>Stream</li>
 * <li>String Slap</li>
 * <li>Strings</li>
 * <li>Sweep Pad</li>
 * <li>Syn Mallet</li>
 * <li>Syn.Calliope</li>
 * <li>Syn.Strings1</li>
 * <li>Syn.Strings2</li>
 * <li>Syn.Strings3</li>
 * <li>SynVox</li>
 * <li>Synth Bass 1</li>
 * <li>Synth Bass 2</li>
 * <li>Synth Bass 3</li>
 * <li>Synth Bass 4</li>
 * <li>Synth Brass1</li>
 * <li>Synth Brass2</li>
 * <li>Synth Brass3</li>
 * <li>Synth Brass4</li>
 * <li>Synth Drum</li>
 * <li>SynthBass101</li>
 * <li>TR-808</li>
 * <li>Taiko</li>
 * <li>Taisho Koto</li>
 * <li>Telephone 1</li>
 * <li>Telephone 2</li>
 * <li>Tenor Sax</li>
 * <li>Thunder</li>
 * <li>Timpani</li>
 * <li>Tinkle Bell</li>
 * <li>Train</li>
 * <li>Tremolo Str</li>
 * <li>Trombone</li>
 * <li>Trombone 2</li>
 * <li>Trumpet</li>
 * <li>Tuba</li>
 * <li>Tubular-bell</li>
 * <li>Ukulele</li>
 * <li>Vibraphone</li>
 * <li>Viola</li>
 * <li>Violin</li>
 * <li>Voice Oohs</li>
 * <li>Warm Pad</li>
 * <li>Whistle</li>
 * <li>Wind</li>
 * <li>Wind Chimes</li>
 * <li>Woodblock</li>
 * <li>Xylophone</li>
 * </ul>
 * 
 * @author Sihn
 *********************************************************************/
public final class Synthe {
	private Synthe() {}

	public final static class Note {
		private int channel;
		private int noteNumber;
		private int duration;

		public Note(int channel, int noteNumber, int duration) {
			this.channel = channel;
			this.noteNumber = noteNumber;
			this.duration = duration;
		}

		public void update() {
			duration--;
		}

		public boolean isDead() {
			return duration <= 0;
		}
	}

	private static Synthesizer synthesizer;
	private static MidiChannel[] channels;
	private static HashMap<String, Instrument> instruments = new HashMap<>();
	private static ArrayList<Note> playedNotes = new ArrayList<>();

	public static void initialize() {
		initialize(null);
	}

	public static void initialize(String bankName) {
		if (synthesizer != null)
			close();
		try {
			synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();
			if (bankName == null)
				synthesizer.loadAllInstruments(synthesizer.getDefaultSoundbank());
			else
				synthesizer.loadAllInstruments(MidiSystem.getSoundbank(new File(bankName)));
			for (Instrument instrument : synthesizer.getLoadedInstruments()) {
				instruments.put(instrument.getName().trim(), instrument);
			}
			channels = synthesizer.getChannels();
		} catch (Exception e) {
			e.printStackTrace();
			synthesizer = null;
		}
	}

	public static void update() {
		if (synthesizer == null)
			return;
		ArrayList<Note> removedNotes = new ArrayList<>();
		for (Note note : playedNotes) {
			note.update();
			if (note.isDead()) {
				channels[note.channel].noteOff(note.noteNumber);
				removedNotes.add(note);
			}
		}
		for (Note note : removedNotes)
			playedNotes.remove(note);
		removedNotes.clear();
	}

	public static void program(int channel, String instrumentName) {
		if (synthesizer == null)
			return;
		Instrument instrument = instruments.get(instrumentName);
		if (instrument == null)
			return;
		Patch patch = instrument.getPatch();
		channels[channel].programChange(patch.getBank(), patch.getProgram());
	}

	public static void play(int channel, int noteNumber, int duration) {
		play(channel, noteNumber, duration, 100);
	}

	public static void play(int channel, int noteNumber, int duration, int velocity) {
		if (synthesizer == null)
			return;
		channels[channel].noteOn(noteNumber, velocity);
		playedNotes.add(new Note(channel, noteNumber, duration));
	}

	public static void close() {
		if (synthesizer == null)
			return;
		synthesizer.close();
	}

	public static Set<String> getInstrumentsList() {
		return instruments.keySet();
	}
}
