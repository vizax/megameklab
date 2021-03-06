/*
 * MegaMekLab - Copyright (C) 2008
 *
 * Original author - jtighe (torren@users.sourceforge.net)
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */

package megameklab.com.util;

import java.util.Comparator;

import megamek.common.Aero;
import megamek.common.AmmoType;
import megamek.common.Dropship;
import megamek.common.Entity;
import megamek.common.EquipmentType;
import megamek.common.Mech;
import megamek.common.MiscType;
import megamek.common.Mounted;
import megamek.common.WeaponType;
import megamek.common.actions.ClubAttackAction;
import megamek.common.actions.KickAttackAction;
import megamek.common.weapons.ACWeapon;
import megamek.common.weapons.ArtilleryCannonWeapon;
import megamek.common.weapons.ArtilleryWeapon;
import megamek.common.weapons.BPodWeapon;
import megamek.common.weapons.CLERSmallPulseLaser;
import megamek.common.weapons.CLMicroPulseLaser;
import megamek.common.weapons.CLPlasmaCannon;
import megamek.common.weapons.CLSmallPulseLaser;
import megamek.common.weapons.CLVehicularGrenadeLauncher;
import megamek.common.weapons.FlamerWeapon;
import megamek.common.weapons.HAGWeapon;
import megamek.common.weapons.ISBombastLaser;
import megamek.common.weapons.ISC3M;
import megamek.common.weapons.ISC3RemoteSensorLauncher;
import megamek.common.weapons.ISHGaussRifle;
import megamek.common.weapons.ISLargeVariableSpeedPulseLaser;
import megamek.common.weapons.ISMediumVariableSpeedPulseLaser;
import megamek.common.weapons.ISPlasmaRifle;
import megamek.common.weapons.ISSilverBulletGauss;
import megamek.common.weapons.ISSmallPulseLaser;
import megamek.common.weapons.ISSmallVariableSpeedPulseLaser;
import megamek.common.weapons.ISSmallXPulseLaser;
import megamek.common.weapons.ISSnubNosePPC;
import megamek.common.weapons.ISThunderBolt10;
import megamek.common.weapons.ISThunderBolt15;
import megamek.common.weapons.ISThunderBolt20;
import megamek.common.weapons.ISThunderBolt5;
import megamek.common.weapons.ISVehicularGrenadeLauncher;
import megamek.common.weapons.LBXACWeapon;
import megamek.common.weapons.LRMWeapon;
import megamek.common.weapons.MGWeapon;
import megamek.common.weapons.MRMWeapon;
import megamek.common.weapons.MekMortarWeapon;
import megamek.common.weapons.NarcWeapon;
import megamek.common.weapons.PPCWeapon;
import megamek.common.weapons.RLWeapon;
import megamek.common.weapons.SRMWeapon;
import megamek.common.weapons.ScreenLauncherWeapon;
import megamek.common.weapons.StreakLRMWeapon;
import megamek.common.weapons.StreakSRMWeapon;
import megamek.common.weapons.TAGWeapon;
import megamek.common.weapons.ThunderBoltWeapon;
import megamek.common.weapons.UACWeapon;
import megamek.common.weapons.battlearmor.CLBAERSmallPulseLaser;
import megamek.common.weapons.battlearmor.CLBALBX;
import megamek.common.weapons.battlearmor.CLBAMicroPulseLaser;
import megamek.common.weapons.battlearmor.CLBASmallPulseLaser;
import megamek.common.weapons.battlearmor.ISBAMediumVariableSpeedPulseLaser;
import megamek.common.weapons.battlearmor.ISBAPopUpMineLauncher;
import megamek.common.weapons.battlearmor.ISBASmallPulseLaser;
import megamek.common.weapons.battlearmor.ISBASmallVariableSpeedPulseLaser;
import megamek.common.weapons.infantry.InfantryWeapon;

public class StringUtils {

    public static Comparator<? super EquipmentType> equipmentTypeComparator() {
        return new Comparator<EquipmentType>() {
            public int compare(EquipmentType eq1, EquipmentType eq2) {
                String s1 = eq1.getName().toLowerCase();
                String s2 = eq2.getName().toLowerCase();
                return s1.compareTo(s2);
            }
        };
    }

    public static Comparator<? super EquipmentInfo> equipmentInfoComparator() {
        return new Comparator<EquipmentInfo>() {
            public int compare(EquipmentInfo eq1, EquipmentInfo eq2) {
                String s1 = eq1.name.toLowerCase();
                String s2 = eq2.name.toLowerCase();
                return s1.compareTo(s2);
            }
        };
    }

    public static Comparator<Mounted> mountedComparator() {
        return new Comparator<Mounted>() {
            public int compare(Mounted m1, Mounted m2) {
                String s1 = m1.getName().toLowerCase();
                String s2 = m2.getName().toLowerCase();
                return s1.compareTo(s2);
            }
        };
    }

    public static String getEquipmentInfo(Dropship unit, Mounted mount, Mounted bay) {
        String info = "";

        if (mount.getType() instanceof WeaponType) {
            WeaponType weapon = (WeaponType) mount.getType();
            if (weapon.getAmmoType() == AmmoType.T_AR10) {
                int barracudaAmmo = 0;
                int killerwhaleAmmo = 0;
                int whitesharkAmmo = 0;
                for (int ammoIndex : bay.getBayAmmo()) {
                    Mounted ammoMount = unit.getEquipment(ammoIndex);
                    try {
                        AmmoType aType = (AmmoType)ammoMount.getType();
                        if ((mount.getLinked() != null) && (aType.getRackSize() == weapon.getRackSize()) && (aType.getAmmoType() == weapon.getAmmoType())) {
                            if (aType.hasFlag(AmmoType.F_AR10_BARRACUDA)) {
                                barracudaAmmo += ammoMount.getUsableShotsLeft();
                            } else if (aType.hasFlag(AmmoType.F_AR10_KILLER_WHALE)) {
                                killerwhaleAmmo += ammoMount.getUsableShotsLeft();
                            } else if (aType.hasFlag(AmmoType.F_AR10_WHITE_SHARK)) {
                               whitesharkAmmo += ammoMount.getUsableShotsLeft();
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                info = "";
                if (barracudaAmmo > 0) {
                    info += String.format("[%1$s Barracuda misl]", barracudaAmmo);
                }
                if (killerwhaleAmmo > 0) {
                    info += String.format("[%1$s Killer Whale misl]", killerwhaleAmmo);
                }
                if (whitesharkAmmo > 0) {
                    info += String.format("[%1$s White Shark misl]", whitesharkAmmo);
                }
            } else if (weapon.getAmmoType() == AmmoType.T_MML) {
                int lrmAmmo = 0;
                int srmAmmo = 0;
                for (int ammoIndex : bay.getBayAmmo()) {
                    Mounted ammoMount = unit.getEquipment(ammoIndex);
                    try {
                        AmmoType aType = (AmmoType)ammoMount.getType();
                        if ((mount.getLinked() != null) && (aType.getRackSize() == weapon.getRackSize()) && (aType.getAmmoType() == weapon.getAmmoType())) {
                            if (aType.hasFlag(AmmoType.F_MML_LRM)) {
                                lrmAmmo += ammoMount.getUsableShotsLeft();
                            } else {
                                srmAmmo += ammoMount.getUsableShotsLeft();
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                info = String.format("[%1$s LRM rnds][%2$s SRM rnds]", lrmAmmo, srmAmmo);
            } else {
                int totalAmmo = 0;
                for (int ammoIndex : bay.getBayAmmo()) {
                    Mounted ammoMount = unit.getEquipment(ammoIndex);
                    try {
                        if ((mount.getLinked() != null) && (ammoMount.getType() == mount.getLinked().getType())) {
                            totalAmmo += ammoMount.getUsableShotsLeft();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                if (weapon instanceof ScreenLauncherWeapon) {
                    info = String.format("[%1$s Screens]", totalAmmo);
                } else if (weapon.hasFlag(WeaponType.F_BALLISTIC) || weapon.hasFlag(WeaponType.F_MISSILE) || weapon.hasFlag(WeaponType.F_ARTILLERY) || (weapon.getAtClass() == WeaponType.CLASS_CAPITAL_MISSILE)) {
                    info = String.format("[%1$s rnds]", totalAmmo);
                }
            }
        }
        return info;

    }

    public static String getEquipmentInfo(Entity unit, Mounted mount) {
        String info = "";

        if (mount.getType() instanceof WeaponType) {
            WeaponType weapon = (WeaponType) mount.getType();
            if (weapon instanceof InfantryWeapon) {
                info = Integer.toString(weapon.getDamage());
                if (weapon.hasFlag(WeaponType.F_BALLISTIC)) {
                    info += " (B)";
                } else if (weapon.hasFlag(WeaponType.F_ENERGY)) {
                    info += " (E)";
                } else if (weapon.hasFlag(WeaponType.F_MISSILE)) {
                    info += " (M)";
                } else if (weapon.hasFlag(WeaponType.F_INF_POINT_BLANK)) {
                    info += " (P)";
                }
                if (weapon.hasFlag(WeaponType.F_INF_BURST)) {
                    info += "B";
                }
                if (weapon.hasFlag(WeaponType.F_INF_AA)) {
                    info += "A";
                }
                if (weapon.hasFlag(WeaponType.F_FLAMER)) {
                    info += "F";
                }
                if (weapon.hasFlag(WeaponType.F_INF_NONPENETRATING)) {
                    info += "N";
                }
            } else if (weapon.hasFlag(WeaponType.F_MGA)) {
                info = "  [T]";
            } else if ((weapon instanceof ISC3M) || (weapon instanceof TAGWeapon)) {
                info = "  [E]";
            } else if (weapon instanceof ISC3RemoteSensorLauncher) {
                info = "  [M,E]";
            } else if (weapon.getDamage() < 0) {
                if (weapon instanceof StreakSRMWeapon) {
                    info = "2/Msl [M,C]";
                } else if ((weapon instanceof SRMWeapon) || (weapon instanceof MekMortarWeapon)) {
                    info = "2/Msl [M,C,S]";
                } else if ((weapon instanceof StreakLRMWeapon)) {
                    info = "1/Msl [M,C]";
                } else if ((weapon instanceof LRMWeapon)) {
                    info = "1/Msl [M,C,S]";
                } else if ((weapon instanceof MRMWeapon) || (weapon instanceof RLWeapon)) {
                    info = "1/Msl [M,C]";
                } else if (weapon instanceof ISSnubNosePPC) {
                    info = "10/8/5 [DE,V]";
                } else if ((weapon instanceof ISSmallVariableSpeedPulseLaser) || (weapon instanceof ISBASmallVariableSpeedPulseLaser)) {
                    info = "5/4/3 [P,V]";
                } else if ((weapon instanceof ISMediumVariableSpeedPulseLaser) || (weapon instanceof ISBAMediumVariableSpeedPulseLaser)) {
                    info = "9/7/5 [P,V]";
                } else if (weapon instanceof ISLargeVariableSpeedPulseLaser) {
                    info = "11/9/7 [P,V]";
                } else if (weapon instanceof ISHGaussRifle) {
                    info = "25/20/10 [DB,X]";
                } else if (weapon instanceof ISPlasmaRifle) {
                    info = "10 [DE,H,AI]";
                } else if (weapon instanceof CLPlasmaCannon) {
                    info = "[DE,H,AI]";
                } else if (weapon instanceof HAGWeapon) {
                    info = Integer.toString(weapon.getRackSize());
                    info += " [C,F,X]";
                } else if (weapon instanceof ArtilleryWeapon) {
                    info = Integer.toString(weapon.getRackSize());
                    info += "[AE,S,F]";
                } else if (weapon instanceof ArtilleryCannonWeapon) {
                    info = Integer.toString(weapon.getRackSize());
                    info += "[DB,AE]";
                } else if (weapon instanceof ThunderBoltWeapon) {
                    if (weapon instanceof ISThunderBolt5) {
                        info = "5";
                    } else if (weapon instanceof ISThunderBolt10) {
                        info = "10";
                    } else if (weapon instanceof ISThunderBolt15) {
                        info = "15";
                    } else if (weapon instanceof ISThunderBolt20) {
                        info = "20";
                    }
                    info += "[M]";
                } else if (weapon instanceof NarcWeapon) {
                    info = "[M]";
                } else if (weapon instanceof ISBAPopUpMineLauncher) {
                    info = "4";
                } else {
                    info = Integer.toString(weapon.getRackSize());
                }
            } else if (weapon instanceof UACWeapon) {
                info = Integer.toString(weapon.getDamage());
                info += "/Sht [DB,R/C]";
            } else if ((weapon instanceof ISVehicularGrenadeLauncher) || (weapon instanceof CLVehicularGrenadeLauncher)) {
                info = "[AE,OS]";
            } else {
                if (!UnitUtil.isAMS(weapon)) {
                    info = Integer.toString(weapon.getDamage());
                }
                info += " [";

                if (weapon.hasFlag(WeaponType.F_BALLISTIC) && !UnitUtil.isAMS(weapon)) {
                    info += "DB,";
                }
                if (UnitUtil.isAMS(weapon) || (weapon.hasFlag(WeaponType.F_B_POD))) {
                    info += "PD,";
                } else if (weapon.hasFlag(WeaponType.F_PULSE)) {
                    info += "P,";
                } else if (weapon.hasFlag(WeaponType.F_ENERGY) || weapon.hasFlag(WeaponType.F_PLASMA)) {
                    info += "DE,";
                }
                if (weapon instanceof ISBombastLaser) {
                    info += "V,";
                }
                if ((weapon instanceof LBXACWeapon) || (weapon instanceof ISSilverBulletGauss)) {
                    info += "C/F/";
                }
                if (weapon instanceof CLBALBX) {
                    info += "C,F,";
                }

                if (UnitUtil.hasSwitchableAmmo(weapon)) {
                    info += "S,";
                }

                if (weapon.hasFlag(WeaponType.F_FLAMER) || weapon.hasFlag(WeaponType.F_PLASMA)) {
                    info += "H,";
                }
                if ((weapon instanceof MGWeapon) || (weapon instanceof BPodWeapon) ||
                        (weapon instanceof CLERSmallPulseLaser) ||
                        (weapon instanceof CLBAERSmallPulseLaser) ||
                        (weapon instanceof ISSmallXPulseLaser) ||
                        (weapon instanceof ISSmallPulseLaser) ||
                        (weapon instanceof ISBASmallPulseLaser) ||
                        (weapon instanceof CLSmallPulseLaser) ||
                        (weapon instanceof CLBASmallPulseLaser) ||
                        (weapon instanceof CLMicroPulseLaser) ||
                        (weapon instanceof CLBAMicroPulseLaser) ||
                        (weapon.hasFlag(WeaponType.F_FLAMER) ||
                        (weapon.hasFlag(WeaponType.F_BURST_FIRE)))) {
                    info += "AI,";
                }

                if (weapon.isExplosive(mount) && !(weapon instanceof ACWeapon) && (!(weapon instanceof PPCWeapon) || ((mount.getLinkedBy() != null) && mount.getLinkedBy().getType().hasFlag(MiscType.F_PPC_CAPACITOR)))) {
                    info += "X,";
                }

                if (weapon.hasFlag(WeaponType.F_ONESHOT)) {
                    info += "OS,";
                }

                info = info.substring(0, info.length() - 1) + "]";

            }
        } else if ((mount.getType() instanceof MiscType) && (mount.getType().hasFlag(MiscType.F_CLUB) || mount.getType().hasFlag(MiscType.F_HAND_WEAPON))) {
            if (mount.getType().hasSubType(MiscType.S_VIBRO_LARGE) || mount.getType().hasSubType(MiscType.S_VIBRO_MEDIUM) || mount.getType().hasSubType(MiscType.S_VIBRO_SMALL)) {
                // manually set vibros to active to get correct damage
                mount.setMode(1);
            }
            if (mount.getType().hasSubType(MiscType.S_CLAW) || mount.getType().hasSubType(MiscType.S_CLAW_THB)) {
                info = Integer.toString((int) Math.ceil(unit.getWeight() / 7.0));
            } else {
                info = Integer.toString(ClubAttackAction.getDamageFor(unit, mount, false));
            }
        } else if ((mount.getType() instanceof MiscType) && (mount.getType().hasFlag(MiscType.F_AP_POD))) {
            info = "[PD,OS,AI]";
        } else if ((mount.getType() instanceof MiscType) && mount.getType().hasFlag(MiscType.F_TALON)) {
            info = Integer.toString(KickAttackAction.getDamageFor(unit, Mech.LOC_LLEG, false));
        } else {
            info = "  [E]";
        }
        return info;
    }

    public static String getEquipmentInfo(Aero unit, Mounted mount) {
        String info = "";

        if (mount.getType() instanceof WeaponType) {
            WeaponType weapon = (WeaponType) mount.getType();
            if (weapon instanceof InfantryWeapon) {
                info = Integer.toString(weapon.getDamage());
                if (weapon.hasFlag(WeaponType.F_BALLISTIC)) {
                    info += " (B)";
                } else if (weapon.hasFlag(WeaponType.F_ENERGY)) {
                    info += " (E)";
                } else if (weapon.hasFlag(WeaponType.F_MISSILE)) {
                    info += " (M)";
                } else if (weapon.hasFlag(WeaponType.F_INF_POINT_BLANK)) {
                    info += " (P)";
                }
                if (weapon.hasFlag(WeaponType.F_INF_BURST)) {
                    info += "B";
                }
                if (weapon.hasFlag(WeaponType.F_INF_AA)) {
                    info += "A";
                }
                if (weapon.hasFlag(WeaponType.F_FLAMER)) {
                    info += "F";
                }
                if (weapon.hasFlag(WeaponType.F_INF_NONPENETRATING)) {
                    info += "N";
                }
            } else if (weapon.hasFlag(WeaponType.F_MGA)) {
                info = "[T]";
            } else if (weapon instanceof ISC3M) {
                info = "[E]";
            } else if (weapon.getDamage() < 0) {
                if (weapon instanceof SRMWeapon) {
                    info = "[M,C]";
                } else if ((weapon instanceof LRMWeapon) || (weapon instanceof MekMortarWeapon)) {
                    info = "[M,C,S]";
                } else if ((weapon instanceof MRMWeapon) || (weapon instanceof RLWeapon)) {
                    info = "[M,C]";
                } else if ((weapon instanceof ISSnubNosePPC) || (weapon instanceof ISBombastLaser)) {
                    info = "[DE,V]";
                } else if (weapon instanceof ISSmallVariableSpeedPulseLaser) {
                    info = "[P,V]";
                } else if (weapon instanceof ISMediumVariableSpeedPulseLaser) {
                    info = "[P,V]";
                } else if (weapon instanceof ISLargeVariableSpeedPulseLaser) {
                    info = "[P,V]";
                } else if (weapon instanceof ISHGaussRifle) {
                    info = "[DB,X]";
                } else if (weapon instanceof ISPlasmaRifle) {
                    info = "[DE,H,AI]";
                } else if (weapon instanceof CLPlasmaCannon) {
                    info = "[DE,H,AI]";
                } else if (weapon instanceof HAGWeapon) {
                    info = "[C,F]";
                } else if (weapon instanceof ArtilleryWeapon) {
                    info = "[AE,S,F]";
                } else if (weapon instanceof ArtilleryCannonWeapon) {
                    info = "[DB,AE]";
                } else if (weapon instanceof ThunderBoltWeapon) {
                    info = "[M]";
                } else if (weapon instanceof NarcWeapon) {
                    info = "[M]";
                } else {
                    info = "";
                }
            } else if (weapon instanceof UACWeapon) {
                info = "[DB,R/C]";
            } else {
                info = " [";

                if (weapon.hasFlag(WeaponType.F_BALLISTIC)) {
                    info += "DB,";
                }
                if (UnitUtil.isAMS(weapon) || (weapon instanceof BPodWeapon)) {
                    info += "PD,";
                } else if (weapon.hasFlag(WeaponType.F_PULSE)) {
                    info += "P,";
                } else if (weapon.hasFlag(WeaponType.F_ENERGY)) {
                    info += "DE,";
                }

                if ((weapon instanceof LBXACWeapon) || (weapon instanceof ISSilverBulletGauss)) {
                    info += "C/F,";
                }

                if (UnitUtil.hasSwitchableAmmo(weapon)) {
                    info += "S,";
                }

                if ((weapon instanceof MGWeapon) || (weapon instanceof BPodWeapon) ||
                        (weapon instanceof CLERSmallPulseLaser) ||
                        (weapon instanceof ISSmallXPulseLaser) ||
                        (weapon instanceof ISSmallPulseLaser) ||
                        (weapon instanceof CLSmallPulseLaser) ||
                        (weapon instanceof CLMicroPulseLaser)) {
                    info += "AI,";
                }

                if (weapon instanceof FlamerWeapon) {
                    info += "H,AI,";
                }

                if (weapon.isExplosive(mount) && !(weapon instanceof ACWeapon) && (!(weapon instanceof PPCWeapon) || ((mount.getLinkedBy() != null) && mount.getLinkedBy().getType().hasFlag(MiscType.F_PPC_CAPACITOR)))) {
                    info += "X,";
                }

                if (weapon.hasFlag(WeaponType.F_ONESHOT)) {
                    info += "OS,";
                }

                info = info.substring(0, info.length() - 1) + "]";

            }
        } else {
            info = "[E]";
        }
        return info;
    }

    public static int countOccurrences(String haystack, char needle)
    {
        int count = 0;
        for (int i=0; i < haystack.length(); i++)
        {
            if (haystack.charAt(i) == needle)
            {
                 count++;
            }
        }
        return count;
    }

}
