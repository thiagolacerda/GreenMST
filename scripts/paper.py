########################################################
#
# Copyright (C) 2013 Luca Prete, Andrea Biancini, Fabio Farina - www.garr.it - Consortium GARR
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# 
# author Luca Prete <luca.prete@garr.it>
# author Andrea Biancini <andrea.biancini@garr.it>
# author Fabio Farina <fabio.farina@garr.it>
#
########################################################

from mininet.topo import Topo

class FourSwitches( Topo ):
    "Simple topology example."

    def __init__( self ):
        "Create custom topo."

        # Initialize topology
        Topo.__init__( self )

        # Add hosts and switches
        h1 = self.addHost( 'h1' )
        h2 = self.addHost( 'h2' )
        h3 = self.addHost( 'h3' )
        h4 = self.addHost( 'h4' )
        h5 = self.addHost( 'h5' )
        h6 = self.addHost( 'h6' )
        h7 = self.addHost( 'h7' )
        h8 = self.addHost( 'h8' )
        h9 = self.addHost( 'h9' )
        h10 = self.addHost( 'h10' )
        h11 = self.addHost( 'h11' )
        h12 = self.addHost( 'h12' )
        h13 = self.addHost( 'h13' )
        h14 = self.addHost( 'h14' )
        h15 = self.addHost( 'h15' )
        h16 = self.addHost( 'h16' )

        s1 = self.addSwitch( 's1' )
        s2 = self.addSwitch( 's2' )
        s3 = self.addSwitch( 's3' )
        s4 = self.addSwitch( 's4' )
        s5 = self.addSwitch( 's5' )
        s6 = self.addSwitch( 's6' )
        s7 = self.addSwitch( 's7' )
        s8 = self.addSwitch( 's8' )
        s9 = self.addSwitch( 's9' )
        s10 = self.addSwitch( 's10' )
        s11 = self.addSwitch( 's11' )
        s12 = self.addSwitch( 's12' )
        s13 = self.addSwitch( 's13' )
        s14 = self.addSwitch( 's14' )
        s15 = self.addSwitch( 's15' )
        s16 = self.addSwitch( 's16' )
        s17 = self.addSwitch( 's17' )
        s18 = self.addSwitch( 's18' )
        s19 = self.addSwitch( 's19' )
        s20 = self.addSwitch( 's20' )

        # Add links
        self.addLink( s1, s5 )
        self.addLink( s1, s7 )
        self.addLink( s1, s9 )
        self.addLink( s1, s11 )

        self.addLink( s2, s5 )
        self.addLink( s2, s7 )
        self.addLink( s2, s9 )
        self.addLink( s2, s11 )

        self.addLink( s3, s6 )
        self.addLink( s3, s8 )
        self.addLink( s3, s10 )
        self.addLink( s3, s12 )

        self.addLink( s4, s6 )
        self.addLink( s4, s8 )
        self.addLink( s4, s10 )
        self.addLink( s4, s12 )

        self.addLink( s5, s13 )
        self.addLink( s5, s14 )
        self.addLink( s6, s13 )
        self.addLink( s6, s14 )

        self.addLink( s7, s15 )
        self.addLink( s7, s16 )
        self.addLink( s8, s15 )
        self.addLink( s8, s16 )

        self.addLink( s9, s17 )
        self.addLink( s9, s18 )
        self.addLink( s10, s17 )
        self.addLink( s10, s18 )

        self.addLink( s11, s19 )
        self.addLink( s11, s20 )
        self.addLink( s12, s19 )
        self.addLink( s12, s20 )

        self.addLink( h1, s13 )
        self.addLink( h2, s13 )

        self.addLink( h3, s14 )
        self.addLink( h4, s14 )

        self.addLink( h5, s15 )
        self.addLink( h6, s15 )

        self.addLink( h7, s16 )
        self.addLink( h8, s16 )

        self.addLink( h9, s17 )
        self.addLink( h10, s17 )

        self.addLink( h11, s18 )
        self.addLink( h12, s18 )

        self.addLink( h13, s19 )
        self.addLink( h14, s19 )

        self.addLink( h15, s20 )
        self.addLink( h16, s20 )


topos = { 'foursw': ( lambda: FourSwitches() ) }
