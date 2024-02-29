package breakout;


import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import breakout.BlockState;
import breakout.BreakoutState;
import breakout.GameMap;
import breakout.PaddleState;
import breakout.utils.*;

class CorrectionTestSuite {
	
	

	
	@Nested
	/**
	 * Those tests should fail on the provided bad implementation, succeed on the model solution.
	 */
	class TaskB {
		
		//fields for testing BreakoutState
		private Point BR;
		private BlockState ablock;
		private BlockState[] someblocks;
		private Ball aball;
		private Ball[] someballs;
		private PaddleState apad;

		@BeforeAll
		static void setUpBeforeClass() throws Exception {
		}

		@BeforeEach
		/**
		 * the BreakoutState tests below depend on the values given here.
		 */
		void setUp() {
			BR = new Point(Constants.WIDTH, Constants.HEIGHT);
			
			ablock = new BlockState(
					new Rect( Constants.ORIGIN, new Point(Constants.BLOCK_WIDTH,Constants.BLOCK_HEIGHT)) );
			someblocks = new BlockState[] { ablock };
			
			apad = new PaddleState(
					new Point( Constants.WIDTH / 2, (3 * Constants.HEIGHT) / 4),
					Constants.TYPICAL_PADDLE_COLORS());
			
			aball =
					new Ball(
						new Circle(
							new Point(BR.getX() / 2 , Constants.HEIGHT / 2)
							, Constants.INIT_BALL_DIAMETER)
						, Constants.INIT_BALL_VELOCITY);
			someballs = new Ball[] { aball };
		}
		
		
		//ABOUT Ball		

		@Test //1
		void ballDefensiveConstructorCircle() {
			assertThrows( IllegalArgumentException.class,
					() -> new Ball(null, new Vector(0,0)));
		}
		
		
		@Test //2
		void ballDefensiveConstructorVector() {
			assertThrows( IllegalArgumentException.class,
					() -> new Ball(new Circle(Constants.ORIGIN, Constants.INIT_BALL_DIAMETER), null));
		}
		
		@Test //3
		/**
		 * The constructor, getLocation() and setLocation() methods do not copy their circle argument.
		 * This is expected since Circle is immutable. 
		 */
		void ballNoCopyOfImmutableFieldCircle() {
			Circle circ = new Circle(Constants.ORIGIN, Constants.INIT_BALL_DIAMETER);
			Ball ball = new Ball(circ, new Vector(0,0));
			assertTrue ( ball.getLocation() == circ);
			ball.setLocation( circ );
			assertTrue ( ball.getLocation() == circ);
			//reference test. this should hold according to assignment
			// "If a class has a field of immutable type..."
		}
		
		@Test //4
		/**
		 * The Ball constructor, getVelocity() and setVelocity() methods do not copy their vector argument.
		 * This is expected since Vector is immutable. 
		 */
		void ballNoCopyOfImmutableFieldVector() {
			
			Vector vec = new Vector( Constants.INIT_BALL_VELOCITY.getX() , Constants.INIT_BALL_VELOCITY.getY() );
			Ball ball = new Ball( new Circle(Constants.ORIGIN, Constants.INIT_BALL_DIAMETER),
								  vec) ;
			assertTrue ( ball.getVelocity() == vec );
			ball.setVelocity( vec );
			assertTrue ( ball.getVelocity() == vec);

		}
		
		
		
		//ABOUT PaddleState

		@Test //5
		/**
		 * The possibleColors field is correctly encaspulated (IN):
		 * modifying an array that has been passed to PaddleState constructor does nothing.
		 */
		void paddlePossibleColorsEncapsIn() {
			Color[] colors = new Color[] {
					Color.green, Color.magenta, Color.orange
			};
			PaddleState pad = new PaddleState(new Point(1000,1000) , colors);
			
			colors[0] = Color.red;
			assertEquals(Color.green, pad.getPossibleColors()[0]);
		}
		
		@Test //6
		/**
		 * The possibleColors field is correctly encapsulated (OUT):
		 * the possibleColors getter does the required copy.
		 */
		void paddlePossibleColorsEncapsOut() {
			PaddleState pad = new PaddleState( new Point(1000, 1000) , Constants.TYPICAL_PADDLE_COLORS() );
			Color[] colors = pad.getPossibleColors();
			colors[0] = null;
			assertNotSame( null , pad.getPossibleColors()[0]);
		}
		
		//BreakoutState
		
		@Test //7
		/**
		 * The BreakoutState constructor makes a deep copy of the ball array.
		 */
		void breakoutStateBallsEncapsIn() {

			
			BreakoutState bstate = new BreakoutState( someballs, someblocks, BR, apad);
			aball.setVelocity( new Vector(0,0));
			
			assertEquals( bstate.getBalls()[0].getVelocity() , Constants.INIT_BALL_VELOCITY );
		}
		
		@Test //8
		/**
		 * The BreakoutState balls getter makes a deep copy of the array
		 */
		void breakoutStateBallsEncapsOut() {
			BreakoutState bstate = new BreakoutState( someballs, someblocks, BR, apad);
			Ball leak = bstate.getBalls()[0];
			leak.setVelocity( new Vector(0,0) );
			assertEquals( bstate.getBalls()[0].getVelocity() , Constants.INIT_BALL_VELOCITY);
			
		}
		
		@Test //9
		/**
		 * The BreakoutState constructor makes a copy of the blocks array
		 */
		void breakoutStateBlocksEncapsIn() {
			BreakoutState bstate = new BreakoutState( someballs, someblocks, BR, apad);
			someblocks[0] = new BlockState(new Rect(Constants.ORIGIN,new Point(2 * Constants.WIDTH, Constants.HEIGHT) ) );
			
			BlockState block = bstate.getBlocks()[0];
			
			assertEquals( ablock.getLocation().getWidth() , block.getLocation().getWidth() );
			
		}
		
		@Test //10
		/**
		 * The BreakoutState blocks getter does a copy
		 */
		void breakoutStateBlocksEncapsOut() {
			BreakoutState bstate = new BreakoutState( someballs, someblocks, BR, apad);
			BlockState[] leak = bstate.getBlocks();
			leak[0] = new BlockState(new Rect(Constants.ORIGIN,new Point(2 * Constants.WIDTH, Constants.HEIGHT) ) );
			
			assertEquals( ablock.getLocation().getWidth() , bstate.getBlocks()[0].getLocation().getWidth() );
		}
		
		
		@Test //11
		/**
		 * The BreakoutState constructor is defensive against a ball array with a null Ball
		 */
		void breakoutStateDefensiveNullBall() {
			assertThrows( IllegalArgumentException.class,
					() -> new BreakoutState( new Ball[] {null}, someblocks, BR, apad) );
		}
		
		@Test //12
		/**
		 * The BreakoutSTate constructor is defensive against a block array with a null block
		 */
		void breakoutStateDefensiveNullBlock() {
			assertThrows( IllegalArgumentException.class , 
					() -> new BreakoutState( someballs , new BlockState[] {null}, BR, apad));
		}
		
		@Test //13
		/**
		 * We randomly pick a paddle color *within* paddle.getPossibleColors()
		 */
		void breakoutStateTossColorInPaddle() {
			BreakoutState bstate = new BreakoutState( someballs , someblocks, BR, apad);
			bstate.tossPaddleColor();
			assertTrue( Arrays.stream(bstate.getPaddle().getPossibleColors()).anyMatch(c -> c .equals(bstate.getCurPaddleColor())) );
		}
		
		@Test //14
		/**
		 * When the ball hits a wall it is not unexpectedly teleported at the center of the field.
		 */
		void breakoutStateNoTeleportBall() {
			//a ball that is going to bounce on the right wall.
			Point nearRightWall = new Point(Constants.WIDTH - (Constants.INIT_BALL_DIAMETER / 2) - 100 , Constants.HEIGHT / 2);
			Vector rightVel = new Vector(15,1);
			Ball myball = new Ball( new Circle(nearRightWall, Constants.INIT_BALL_DIAMETER) , rightVel);
			
			BreakoutState bstate = new BreakoutState( new Ball[] { myball }, someblocks, BR, apad);
			
			bstate.tickDuring(200);
			
			assertNotEquals( new Vector(15,1) , bstate.getBalls()[0].getVelocity() ); //has bounced
			assertTrue( bstate.getBalls()[0].getCenter().getX() > (Constants.WIDTH * 3) / 4 ); //has remained near right wall
		}
		
		@Test //15
		/**
		 * When the ball hits a block the paddle is not unexpectedly attracted towards the center
		 */
		void breakoutStateNoPaddleFlick() {
			//a ball about to hit a block located at the top left corner, from below
			Point nearTopLeftBlock = new Point(Constants.BLOCK_WIDTH / 2, Constants.BLOCK_HEIGHT + 300);
			Vector upVelocity = new Vector(0, -10);
			Ball myball = new Ball( new Circle(nearTopLeftBlock, Constants.INIT_BALL_DIAMETER) , upVelocity );
			
			//the paddle is to the right of the center of the game field
			PaddleState mypad = new PaddleState( new Point(Constants.WIDTH * 3 / 4, Constants.HEIGHT * 3 / 4) ,Constants.TYPICAL_PADDLE_COLORS());
			Point initPadLocation = mypad.getCenter();
			
			BreakoutState bstate = new BreakoutState( new Ball[] {myball} , someblocks, BR, mypad);
			bstate.tickDuring(200);
			
			assertEquals( initPadLocation, bstate.getPaddle().getCenter());
		}
		
		@Test //16
		/**
		 * Moving the paddle to the right during x ms, and then to the left during x ms is the same than doing nothing.
		 */
		void breakoutStateMovePaddleLeftRight() {
			BreakoutState bstate = new BreakoutState(someballs, someblocks, BR, apad);
			Point initPadLocation = apad.getCenter();
			
			bstate.movePaddleLeft(200);
			bstate.movePaddleRight(200);
			
			assertEquals( initPadLocation, bstate.getPaddle().getCenter());
		}
		
	}
	
	@Nested
	/**
	 * Those tests check that a specific class invariant or a precondition on the constructor
	 * is present
	 */
	class MissingSpecTests {
		
		@Test
		/**
		 * There is a "every possible color is non-null" private or public invariant,
		 * or a precondition in the PaddleState constructor
		 */
		void paddleNonNullColors() {
			Color[] colors = new Color[] {
					Color.green, Color.magenta, null
			};
			try {
				new PaddleState( new Point(1000, 1000) , colors);
				throw new Exception();
			} catch (AssertionError e) {
				assert true;
			} catch (Exception e) {
				fail("Expected an assertion error caused by a null color");
			}
		}
		
		  @Test
		  /**
		   * Ball.move has a `!= null` precondition (or some of the method it  uses)
		   */
		  void ballMovePre() {
			  try {
				  Ball ball = new Ball(new Circle(Constants.ORIGIN, Constants.INIT_BALL_DIAMETER) , Constants.INIT_BALL_VELOCITY);
				  ball.move(null);
				  throw new Exception();
			  } catch (AssertionError e) {
				  assert true;
			  } catch (Exception e) {
				  fail("Assertion error was expected because of null argument");
			  }

		  }
		  
		  //TODO Breakout invars: curPaddleColor membership invar
		
	}
	
	@Nested
	/**
	 * correction tests for task A
	 */
	class TaskA {
		
		//fields for testing BreakoutState
		private Point BR;
		private BlockState ablock;
		private BlockState[] someblocks;
		private Ball aball;
		private Ball[] someballs;
		private PaddleState apad;

		@BeforeAll
		static void setUpBeforeClass() throws Exception {
		}

		@BeforeEach
		/**
		 * the BreakoutState tests below depend on the values given here.
		 */
		void setUp() {
			BR = new Point(Constants.WIDTH, Constants.HEIGHT);
			
			ablock = new BlockState(
					new Rect( Constants.ORIGIN, new Point(Constants.BLOCK_WIDTH,Constants.BLOCK_HEIGHT)) );
			someblocks = new BlockState[] { ablock };
			
			apad = new PaddleState(
					new Point( Constants.WIDTH / 2, (3 * Constants.HEIGHT) / 4),
					Constants.TYPICAL_PADDLE_COLORS());
			
			aball =
					new Ball(
						new Circle(
							new Point(BR.getX() / 2 , Constants.HEIGHT / 2)
							, Constants.INIT_BALL_DIAMETER)
						, Constants.INIT_BALL_VELOCITY);
			someballs = new Ball[] { aball };
		}
		
		@Test
		/**
		 * BlockState constructor has != null pre.
		 */
		void blockStateCstrPre() {
			try {
				new BlockState( null );
				throw new Exception();
			} catch (AssertionError e) {
				assert true; //theres a fsc4j precondition as expected
			} catch (Exception e) {
				fail("Expected fsc4j arg != null precondition in BlockState constructor");
			}
		}
		
		@Test
		/**
		 * Vector.plus has != null pre
		 */
		void vectorPlusPre() {
			try {
				Vector avec = new Vector(100, 200);
				avec.plus(null);
				throw new Exception();
			} catch (AssertionError e) {
				assert true; //theres a fsc4j precond as expected
			} catch (Exception e) {
				fail("Expected fsc4j arg != null precond in Vector.plus");
			}
		}
		
		@Test
		/**
		 * An out of field ball is removed 
		 */
		void removeDeadBallTest() {
			Circle newcirc = new Circle( new Point(Constants.WIDTH / 2 , Constants.HEIGHT - 700), Constants.INIT_BALL_DIAMETER);
			// the above is almost out of field
			aball.setLocation(newcirc);
			aball.setVelocity(new Vector(0,20));
			BreakoutState bstate = new BreakoutState (someballs, someblocks, BR, apad );
			assertEquals(1, bstate.getBalls().length);
			bstate.tickDuring(1000);
			assertEquals(0, bstate.getBalls().length);
		}
	}

	

}
